package com.tianatonnu.handymaps;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.SearchView;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.Property.NONE;
import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

public class MapController {
    private MainActivity mainActivity;

    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    private Point destinationPoint;

    private MapboxMap mapboxMap;
    private MapView mapView;
    private LocationComponent locationComponent;

    private SearchView searchView;

    public MapController(MainActivity mainActivity, LocationComponent locationComponent, MapboxMap mapboxMap, MapView mapView)
    {
        this.mainActivity = mainActivity;
        this.locationComponent = locationComponent;
        this.mapboxMap = mapboxMap;
        this.mapView = mapView;
    }

    public void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.mapbox_marker_icon_default));
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

    public void mapClickFunctionality(@NonNull LatLng point, MapButtonsController buttonsController)
    {
        destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());

        // Make the way point marker visible
        Layer layer = mapboxMap.getStyle().getLayer("destination-symbol-layer-id");
        if (layer != null)
        {
            layer.setProperties(visibility(VISIBLE));
        }

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }

        // Remove the current route
        if (navigationMapRoute != null)
            navigationMapRoute.removeRoute();

        buttonsController.enableAllButtons();
    }

    public void findLocation(MapButtonsController buttonsController, Point destinationPoint)
    {
        //destinationPoint = Point.fromLngLat(classLocation.getLongitude(), classLocation.getLatitude());
        this.destinationPoint = destinationPoint;

        Layer layer = mapboxMap.getStyle().getLayer("destination-symbol-layer-id");
        if (layer != null) {
            layer.setProperties(visibility(VISIBLE));
        }

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }
        mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(destinationPoint.latitude(), destinationPoint.longitude()), 16));

        // Remove the current route
        if (navigationMapRoute != null)
            navigationMapRoute.removeRoute();
        // Enable the route and clear buttons
        buttonsController.enableAllButtons();
    }

    public void makeRoute()
    {
        getRoute(destinationPoint);
    }

    public void startNavigation()
    {
        boolean simulateRoute = false;
        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                .directionsRoute(currentRoute)
                .shouldSimulateRoute(simulateRoute)
                .build();
        // Call this method with Context from within an Activity
        NavigationLauncher.startNavigation(this.mainActivity, options);
    }

    private void getRoute(Point destination) {
        Point origin = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        NavigationRoute.builder(this.mainActivity)
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

    public void centerOnCampus()
    {
        mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.300559, -120.661090), 16));
    }

    public void centerOnUser()
    {
        mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationComponent.getLastKnownLocation().getLatitude(),
                locationComponent.getLastKnownLocation().getLongitude()), 16));
    }

    public void clearMap()
    {
        // Remove the route
        if (navigationMapRoute != null)
            navigationMapRoute.removeRoute();

        // Remove the way point marker
        Layer layer = mapboxMap.getStyle().getLayer("destination-symbol-layer-id");
        if (layer != null)
        {
            layer.setProperties(visibility(NONE));
        }
    }


}
