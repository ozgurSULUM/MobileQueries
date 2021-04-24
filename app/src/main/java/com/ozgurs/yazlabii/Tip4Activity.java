package com.ozgurs.yazlabii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tip4Activity extends AppCompatActivity implements OnMapReadyCallback, Callback<DirectionsResponse> {
    public MapView mapView;
    public MapboxMap mapboxMap;
    public Point origin;
    public Point destination;
    public static final String ROUTE_SOURCE_ID = "route-source-id";
    public static final String ROUTE_LAYER_ID = "route-layer-id";
    public static final String ICON_SOURCE_ID = "icon-source-id";
    public static final String ICON_LAYER_ID = "icon-layer-id";
    public static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    public MapboxDirections client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this,getString(R.string.APP_TOKEN));
        setContentView(R.layout.activity_tip4);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        Intent intent = getIntent();
        Lat_Lng d1 = (Lat_Lng) intent.getSerializableExtra("latLng_pickup");
        Lat_Lng d2 = (Lat_Lng) intent.getSerializableExtra("latLng_drop");
        origin = Point.fromLngLat(d1.getLng(),d1.getLat());
        destination = Point.fromLngLat(d2.getLng(),d2.getLat());
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded(){
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                initSource(style);
                initLayers(style);

                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(origin.latitude(),origin.longitude()))
                        .zoom(10)
                        .tilt(13)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position),1000);


                getRoute(mapboxMap,origin,destination);
            }
        });
    }

    private void initLayers(@NonNull Style loadedMapStyle){
        LineLayer routeLayer = new LineLayer(ROUTE_LAYER_ID,ROUTE_SOURCE_ID);

        routeLayer.setProperties(
                PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                PropertyFactory.lineWidth(5f),
                PropertyFactory.lineColor(Color.parseColor("#009688"))

        );
        loadedMapStyle.addLayer(routeLayer);

        loadedMapStyle.addImage(RED_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.marker)));

        loadedMapStyle.addLayer(new SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(
                PropertyFactory.iconImage(RED_PIN_ICON_ID),
                PropertyFactory.iconIgnorePlacement(true),
                PropertyFactory.iconAllowOverlap(true),
                PropertyFactory.iconOffset(new Float[] {0f, -9f})));
    }

    private void initSource(@NonNull Style loadedMapStyle){
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID));

        GeoJsonSource iconGeoJsonSource = new GeoJsonSource(ICON_SOURCE_ID, FeatureCollection.fromFeatures(new Feature[]{
                Feature.fromGeometry(Point.fromLngLat(origin.longitude(), origin.latitude())),
                Feature.fromGeometry(Point.fromLngLat(destination.longitude(),destination.latitude()))
        }));
        loadedMapStyle.addSource(iconGeoJsonSource);
    }

    @Override
    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
        if(response.body() == null){
            Toast.makeText(this,"No routes found, make sure you set the right user and access token.",Toast.LENGTH_SHORT).show();
            return;
        } else if(response.body().routes().size() < 1){
            Toast.makeText(this, "No routes found.",Toast.LENGTH_SHORT).show();
        }

        DirectionsRoute currentRoute = response.body().routes().get(0);

        if(mapboxMap != null){
            mapboxMap.getStyle(new Style.OnStyleLoaded(){
                @Override
                public void onStyleLoaded(@NonNull Style style) {

                    GeoJsonSource source = style.getSourceAs(ROUTE_SOURCE_ID);
                    if(source != null){
                        source.setGeoJson(LineString.fromPolyline(currentRoute.geometry(), Constants.PRECISION_6));
                    }

                }
            });
        }

    }

    @Override
    public void onFailure(Call<DirectionsResponse> call, Throwable t) {

    }

    private void getRoute(MapboxMap mapboxMap, Point origin, Point destination){
        client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken(Mapbox.getAccessToken())
                .build();
        client.enqueueCall(this);

    }
}