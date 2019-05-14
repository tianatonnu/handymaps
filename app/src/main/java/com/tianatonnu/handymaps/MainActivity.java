package com.tianatonnu.handymaps;

import android.app.ListActivity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

// classes needed to add a marker
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.mapbox.mapboxsdk.style.layers.Property.NONE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

// classes to calculate a route
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

// classes needed to launch navigation UI
import android.view.View;
import android.widget.Button;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.mapbox.mapboxsdk.annotations.Marker;
//import com.mapbox.services.commons.models.Position;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Use the LocationComponent to easily add a device location "puck" to a Mapbox map.
 */
public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener {

    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private LocationComponent locationComponent;

    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    private Point destinationPoint;
    private Point originPoint;

    // variables needed to initialize navigation
    private Button findButton;
    private Button startButton;
    private Button clearBtn;
    private Button routeBtn;
    private FloatingActionButton centerBtn;
    private int center = 0;

    // Arrays with all data objects
    private Course[] courses;
    private Classroom[] classRooms;
    private Building[] buildings;

    // Arrays with all data strings
    private String[] courseStrings;
    private String[] buildingStrings;
    private String[] classRoomStrings;
    private ArrayList<String> allData = new ArrayList<>();

    // Search variables
    private ArrayAdapter<String> adapter;
    private TextView tv;
    private SearchView searchView;
    private ListView listView;
    private int clear = 0, start = 0, find = 0, route = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        centerBtn = findViewById(R.id.center_toggle);
        routeBtn = findViewById(R.id.routeButton);
        clearBtn = findViewById(R.id.clearButton);

        // Get the data
        courses = JSONParser.getCourses();
        buildings = JSONParser.getBuildings();
        classRooms = JSONParser.getClassrooms();

        // Turn the data into strings
        courseStrings = JSONParser.makeStrings(courses);
        buildingStrings = JSONParser.makeStrings(buildings);
        classRoomStrings = JSONParser.makeStrings(classRooms);

        // Sort the data
        Arrays.sort(courseStrings);
        Arrays.sort(buildingStrings);
        Arrays.sort(classRoomStrings);

        // Put all the data into a single list
        Collections.addAll(allData, buildingStrings);
        Collections.addAll(allData, classRoomStrings);
        Collections.addAll(allData, courseStrings);

        list();
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        MainActivity.this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(new Style.Builder().fromUrl("mapbox://styles/kevinsundar/cjt68puf75bui1fr0tfx397hw"),
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);

                        addDestinationIconSymbolLayer(style);

                        mapboxMap.addOnMapClickListener(MainActivity.this);

                        routeBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Need to calculate route
                                if (routeBtn.getText().equals(getResources().getString(R.string.find)))
                                {
                                    // Find route
                                    getRoute(originPoint, destinationPoint);
                                    routeBtn.setText(getResources().getString(R.string.start));
                                    routeBtn.setBackgroundColor(getResources().getColor(R.color.startGreen));
                                }
                                // Need to start route
                                else
                                {
                                    boolean simulateRoute = false;
                                    NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                            .directionsRoute(currentRoute)
                                            .shouldSimulateRoute(simulateRoute)
                                            .build();
                                    // Call this method with Context from within an Activity
                                    NavigationLauncher.startNavigation(MainActivity.this, options);
                                }
                            }
                        });

                        clearBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Remove the route
                                if (navigationMapRoute != null)
                                    navigationMapRoute.removeRoute();

                                // Remove the way point marker
                                Layer layer = mapboxMap.getStyle().getLayer("destination-symbol-layer-id");
                                if (layer != null)
                                {
                                    layer.setProperties(visibility(NONE));
                                }

                                routeBtn.setText(getResources().getString(R.string.find));
                                routeBtn.setBackgroundColor(getResources().getColor(R.color.mapbox_blue));
                                routeBtn.setEnabled(false);
                                routeBtn.setVisibility(View.INVISIBLE);
                                route = 0;
                                clearBtn.setEnabled(false);
                                clearBtn.setVisibility(View.INVISIBLE);
                                clear = 0;
                            }
                        });

                        centerBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Switch to center on user's current location
                                if (center == 0)
                                {
                                    center = 1;
                                    mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationComponent.getLastKnownLocation().getLatitude(),
                                            locationComponent.getLastKnownLocation().getLongitude()), 16));
                                    centerBtn.setImageResource(R.drawable.ic_my_location_24dp);
                                }
                                // Switch to centered on middle of campus
                                else
                                {
                                    center = 0;
                                    mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.300559, -120.661090), 16));
                                    centerBtn.setImageResource(R.drawable.ic_location_disabled_24dp);
                                }
                            }
                        });
                    }
                });
    }

    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true),
                visibility(VISIBLE)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        // Make the way point marker visible
        Layer layer = mapboxMap.getStyle().getLayer("destination-symbol-layer-id");
        if (layer != null)
        {
            layer.setProperties(visibility(VISIBLE));
        }

        destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }

        //getRoute(originPoint, destinationPoint);
        // Remove the current route
        if (navigationMapRoute != null)
            navigationMapRoute.removeRoute();
        routeBtn.setEnabled(true);
        routeBtn.setVisibility(View.VISIBLE);
        routeBtn.setBackgroundColor(getResources().getColor(R.color.mapbox_blue));
        routeBtn.setText(getResources().getString(R.string.find));
        route = 1;
        clearBtn.setEnabled(true);
        clearBtn.setVisibility(View.VISIBLE);
        clear = 1;
        return true;
    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();

            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            //locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setCameraMode(CameraMode.NONE_GPS);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    // Search stuff
    public void list() {

        ListView lv = (ListView) findViewById(R.id.ListView);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                allData);

        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Handle the search bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        ListView lv = (ListView) findViewById(R.id.ListView);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem item = menu.findItem(R.id.search);
        /*SearchView*/ searchView = (SearchView) MenuItemCompat.getActionView(item); //item.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.hint));

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Hide results
                findViewById(R.id.ListView).setVisibility(View.INVISIBLE);

                // Re-enable the buttons
                centerBtn.setVisibility(View.VISIBLE);
                if (clear == 1)
                {
                    clearBtn.setEnabled(true);
                    clearBtn.setVisibility(View.VISIBLE);
                }
                if (route == 1)
                {
                    routeBtn.setEnabled(false);
                    routeBtn.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Display results
                findViewById(R.id.ListView).setVisibility(View.VISIBLE);

                // Hide all buttons
                routeBtn.setEnabled(false);
                routeBtn.setVisibility(View.INVISIBLE);
                clearBtn.setEnabled(false);
                clearBtn.setVisibility(View.INVISIBLE);
                centerBtn.setVisibility(View.INVISIBLE);

                //adapter.getFilter().filter(newText);
                if (newText != null && !newText.isEmpty())
                {
                    // Filter options
                    // Make this more robust
                    ArrayList<String> lstFound = new ArrayList<>();

                    // Split search into separate words (key words)
                    String[] keyWords = newText.split(" ");
                    for (String item:allData)
                    {
                        int valid = 1;
                        for (String word:keyWords)
                        {
                            // item does not contain the key word
                            if (!item.toLowerCase().contains(word.toLowerCase()))
                            {
                                valid = 0;
                                break;
                            }
                        }
                        // Item contains all key words
                        if (valid == 1)
                        {
                            lstFound.add(item);
                        }
                    }
                    // Return the filtered results
                    adapter = new ArrayAdapter<>(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            lstFound);

                    lv.setAdapter(adapter);
                }

                else
                {
                    // If no search, return all options
                    adapter = new ArrayAdapter<>(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            allData);

                    lv.setAdapter(adapter);
                }

                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchView.clearFocus();
                findViewById(R.id.ListView).setVisibility(View.INVISIBLE);
                String card = adapter.getItem(position);
                Log.d("Search", card);
                int found = 0;

                // Search buildings
                for (Building building:buildings)
                {
                    if (building.createCard().equals(card))
                    {
                        destinationPoint = Point.fromLngLat(building.getBuildingLong(), building.getBuildingLat());
                        found = 1;
                        //Log.d("Search", building.createCard());
                        Log.d("Search", "/n" + building.createCard() + " Longitude: " + destinationPoint.longitude() + " Latitude: " + destinationPoint.latitude());
                        break;
                    }
                }

                // Search courses
                for (Course course:courses)
                {
                    // No need to search the courses
                    if (found == 1)
                        break;
                    // Need to search the courses
                    if (course.createCard().equals(card))
                    {
                        destinationPoint = Point.fromLngLat(course.getCourseLong(), course.getCourseLat());
                        found = 1;
                        //Log.d("Search", course.createCard());
                        Log.d("Search", "/n" + course.createCard() + " Longitude: " + destinationPoint.longitude() + " Latitude: " + destinationPoint.latitude());
                        break;
                    }
                }

                // Search classRooms
                for (Classroom classroom:classRooms)
                {
                    // No need to search the classRooms
                    if (found == 1)
                        break;
                    // Need to search the classRooms
                    if (classroom.createCard().equals(card))
                    {
                        destinationPoint = Point.fromLngLat(classroom.getClassLong(), classroom.getClassLat());
                        found = 1;
                        //Log.d("Search", classroom.createCard());
                        Log.d("Search", "/n" + classroom.createCard() + " Longitude: " + destinationPoint.longitude() + " Latitude: " + destinationPoint.latitude());
                        break;
                    }
                }

                Style style = mapboxMap.getStyle();
                if (style != null)
                {
                    Layer layer = style.getLayer("destination-symbol-layer-id");
                    if (layer != null) {
                        layer.setProperties(visibility(VISIBLE));
                    }

                    originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                            locationComponent.getLastKnownLocation().getLatitude());

                    GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
                    if (source != null) {
                        source.setGeoJson(Feature.fromGeometry(destinationPoint));
                    }
                    mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(destinationPoint.latitude(), destinationPoint.longitude()), 16));

                    // Remove the current route
                    if (navigationMapRoute != null)
                        navigationMapRoute.removeRoute();
                    // Enable the route and clear buttons
                    routeBtn.setEnabled(true);
                    routeBtn.setVisibility(View.VISIBLE);
                    routeBtn.setBackgroundColor(getResources().getColor(R.color.mapbox_blue));
                    routeBtn.setText(getResources().getString(R.string.find));
                    route = 1;
                    clearBtn.setEnabled(true);
                    clearBtn.setVisibility(View.VISIBLE);
                    clear = 1;
                    centerBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.ListView).setVisibility(View.INVISIBLE);
        return true;
    }


    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}