package com.example.mapboxtest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Mapbox.getInstance(this, "pk.eyJ1IjoicmljaGFyZGp1bmciLCJhIjoiY2oyOTdma3EwMDA2cTJxbXgwMGt1MWI1aCJ9.d2pGP-GfbVszdIzT-CdJHA");
        Mapbox.getInstance(this, "pk.eyJ1Ijoia2V2aW5zdW5kYXIiLCJhIjoiY2lmNjF6aGdmMDM1MDdzbHVpcThyZjF4YyJ9.1NKie7hjfYG3dNPYLhRizA");
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(/*Style.LIGHT, new Style.OnStyleLoaded()*/new Style.Builder().fromUrl("mapbox://styles/kevinsundar/cjt68puf75bui1fr0tfx397hw"), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        // Add the marker image to map
                        style.addImage("marker-icon-id", BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.mapbox_marker_icon_default));

                        GeoJsonSource geoJsonSource = new GeoJsonSource("source-id", Feature.fromGeometry(Point.fromLngLat(-120.661090, 35.300559)));
                        style.addSource(geoJsonSource);

                        SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
                        symbolLayer.withProperties(PropertyFactory.iconImage("marker-icon-id"));
                        style.addLayer(symbolLayer);
                    }
                });
            }
        });
        /*mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

// Map is set up and the style has loaded. Now you can add data or make other map adjustments


                    }
                });
            }
        });*/
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
