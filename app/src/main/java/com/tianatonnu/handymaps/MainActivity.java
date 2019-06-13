package com.tianatonnu.handymaps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

// classes needed to add a marker
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;

// classes needed to launch navigation UI
import android.view.View;
import java.util.Collections;
import java.util.List;

// Classes for the search bar
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

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
    private Point destinationPoint;

    // Arrays with all data objects
    private Course[] courses;
    private Classroom[] classRooms;
    private Building[] buildings;
    private ArrayList<Location> locations = new ArrayList<>();

    DestinationPoint classLocation = null;

    // Arrays with all data strings
    private String[] courseStrings;
    private String[] buildingStrings;
    private String[] classRoomStrings;
    private ArrayList<String> allData = new ArrayList<>();

    // Search variables
    private ArrayAdapter<String> adapter;
    private SearchView searchView;

    MapButtonsController buttonsController;
    MapController mapController;
    MapSearchViewController searchViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Get the data
        courses = JSONParser.getCourses("sections.json");
        buildings = JSONParser.getBuildings("buildings.json");
        classRooms = JSONParser.getClassrooms("classrooms.json");

        // Sort the data
        Arrays.sort(courses);
        Arrays.sort(buildings);
        Arrays.sort(classRooms);

        // Load location data into one arraylist
        Collections.addAll(locations, courses);
        Collections.addAll(locations, buildings);
        Collections.addAll(locations, classRooms);

        // Turn the data into strings
        courseStrings = JSONParser.makeStrings(courses);
        buildingStrings = JSONParser.makeStrings(buildings);
        classRoomStrings = JSONParser.makeStrings(classRooms);

        // Put all the data into a single list
        Collections.addAll(allData, buildingStrings);
        Collections.addAll(allData, classRoomStrings);
        Collections.addAll(allData, courseStrings);

        classLocation = (DestinationPoint)getIntent().getSerializableExtra("classLocation");
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        MainActivity.this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(new Style.Builder().fromUrl("mapbox://styles/kevinsundar/cjt68puf75bui1fr0tfx397hw"),
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);

                        mapboxMap.addOnMapClickListener(MainActivity.this);

                        mapController = new MapController(MainActivity.this, locationComponent, mapboxMap, mapView);
                        buttonsController = new MapButtonsController(MainActivity.this, mapController);

                        mapController.addDestinationIconSymbolLayer(style);

                        // Set up the on click listeners for the search bar
                        ListView lv = findViewById(R.id.ListView);
                        searchViewController = new MapSearchViewController(MainActivity.this, searchView, lv,
                                mapController, allData);
                        searchViewController.setSearchViewListeners(mapboxMap, locations, buttonsController);

                        // If looking for class from schedule, place marker at class location
                        if (classLocation != null)
                        {
                            Point point = Point.fromLngLat(classLocation.getLongitude(), classLocation.getLatitude());
                            mapController.findLocation(buttonsController, point);
                        }

                        // Set the on click listeners for all the buttons
                        buttonsController.setButtonListeners();
                    }
                });
    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        // Set the functionality for when the map is clicked on
        mapController.mapClickFunctionality(point, buttonsController);

        // Clear search bar if it is up
        searchView.clearFocus();
        findViewById(R.id.ListView).setVisibility(View.INVISIBLE);

        return true;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Handle the search bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // Set up the search bar
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem item = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.hint));

        findViewById(R.id.ListView).setVisibility(View.INVISIBLE);
        return true;
    }

    /* Added by Tatjana 05/15. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Open the schedule page
            case R.id.schedule:
                startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setSearchAdapter(ArrayList<String> results, ListView listView)
    {
        adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                results);

        listView.setAdapter(adapter);
    }

    public String getSearchItemString(int position)
    {
        return adapter.getItem(position);
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