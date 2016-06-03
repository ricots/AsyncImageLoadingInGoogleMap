package com.rajesh.custominfowindowwithcustommarkeringooglemap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;

    private ArrayList<MapViewResponse> polyLinePoints;
    private HashMap<Marker, Integer> mHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (googleMap == null) {

            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_view);

        }

        MapsInitializer.initialize(this);

        if (mapFragment != null) {

           // if (Utility.isGoogleMapsInstalled()) {
                mapFragment.getMapAsync(MapActivity.this);

            /*} else {
                Utility.showCustomToastAlert(this, "Please install google map", AppConstants.ToastTypes.TOAST_ERROR);
            }*/
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;


        googleMap.setTrafficEnabled(false);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setOnMarkerClickListener(MapActivity.this);
        googleMap.setMyLocationEnabled(true);

        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        getLanLongs();

        setZoomToMarkers();


    }

    private void getLanLongs() {

        polyLinePoints = new ArrayList<>();
        MapViewResponse mapModel;

        LatLng LatLng;

        LatLng = new LatLng(23.0069895, 72.5033195);
        mapModel = new MapViewResponse();
        mapModel.setLatlng(LatLng);
        mapModel.setUserName("Rajesh Koshti");
        mapModel.setUserImageUrl("http://microblogging.wingnity.com/JSONParsingTutorial/brad.jpg");
        mapModel.setAddress("Prahaladnagar Garden Ahmedab");
        polyLinePoints.add(mapModel);

        LatLng = new LatLng(23.006599, 72.504596);
        mapModel = new MapViewResponse();
        mapModel.setLatlng(LatLng);
        mapModel.setUserName("Pratik");
        mapModel.setUserImageUrl("http://microblogging.wingnity.com/JSONParsingTutorial/cruise.jpg");
        mapModel.setAddress("Prahaladnagar Garden Ahmedab");
        polyLinePoints.add(mapModel);

        LatLng = new LatLng(23.006599, 72.5033195);
        mapModel = new MapViewResponse();
        mapModel.setLatlng(LatLng);
        mapModel.setUserName("Sagar");
        mapModel.setUserImageUrl("http://microblogging.wingnity.com/JSONParsingTutorial/johnny.jpg");
        mapModel.setAddress("Prahaladnagar Garden Ahmedabad");
        polyLinePoints.add(mapModel);

        LatLng = new LatLng(23.006793, 72.503974);
        mapModel = new MapViewResponse();
        mapModel.setLatlng(LatLng);
        mapModel.setUserName("Manish");
        mapModel.setUserImageUrl("http://microblogging.wingnity.com/JSONParsingTutorial/jolie.jpg");
        mapModel.setAddress("Prahaladnagar Garden Ahmedab");
        polyLinePoints.add(mapModel);

        LatLng = new LatLng(23.006605, 72.505165);
        mapModel = new MapViewResponse();
        mapModel.setLatlng(LatLng);
        mapModel.setUserName("Sunil");
        mapModel.setUserImageUrl("http://microblogging.wingnity.com/JSONParsingTutorial/tom.jpg");
        mapModel.setAddress("Prahaladnagar Garden Ahmedab");
        polyLinePoints.add(mapModel);

        for (int i = 0; i < polyLinePoints.size(); i++) {

            if (googleMap != null) {

                View markerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_image_marker, null);
                ImageView iv = (ImageView) markerView.findViewById(R.id.profile_image);

                if(!TextUtils.isEmpty(polyLinePoints.get(i).getUserImageUrl())) {

                    Picasso.with(MapActivity.this)
                            .load(polyLinePoints.get(i).getUserImageUrl())
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .into(iv, new UserImageMarkerCallBack(i, iv, markerView));

                }


                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(polyLinePoints.get(i).getLatlng())
                        .title(polyLinePoints.get(i).getUserName())
                        .snippet(polyLinePoints.get(i).getAddress())
                        .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(markerView))));

                mHashMap.put(marker, i);


            }
        }

    }

    private void setZoomToMarkers() {

        if(mHashMap.size()>0){

            int zoomingPosition = 0;
            if (polyLinePoints.size() > 1) {
                zoomingPosition = polyLinePoints.size() / 2;
            }

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(polyLinePoints.get(zoomingPosition).getLatlng())
                    .zoom(17)
                    .build();

            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }


    }

    public class UserImageMarkerCallBack implements Callback {

        // Marker marker=null;

        int position;
        ImageView imageView;
        View layoutView;

        UserImageMarkerCallBack(int pos,ImageView iv,View view) {
            this.position=pos;
            this.imageView = iv;
            this.layoutView = view;
        }

        @Override
        public void onError() {
            Log.e(getClass().getSimpleName(), "Error loading thumbnail!");
        }

        @Override
        public void onSuccess() {

            for (Map.Entry<Marker, Integer> entry : mHashMap.entrySet()) {
                if (entry.getValue().equals(position)) {

                    if(!TextUtils.isEmpty(polyLinePoints.get(position).getUserImageUrl())){

                        Picasso.with(MapActivity.this)
                                .load(polyLinePoints.get(position).getUserImageUrl())
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .into(imageView);
                    }

                    Marker m = entry.getKey();

                    if(m != null)
                        m.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(layoutView)));

                }
            }

        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public static Bitmap createDrawableFromView( View customMarkerView) {
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);

        return returnedBitmap;
    }

    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private View view;

        public CustomInfoWindowAdapter() {
            view = getLayoutInflater().inflate(R.layout.custom_info_window,
                    null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            if (marker != null
                    && marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
                marker.showInfoWindow();
            }
            return null;
        }

        @Override
        public View getInfoWindow(final Marker marker) {

            String url, title, snippet;

            ImageView ivUserImage = ((ImageView) view.findViewById(R.id.iv_user_image));
            TextView tvUserName = ((TextView) view.findViewById(R.id.tv_username));
            TextView tvUserAddress = ((TextView) view.findViewById(R.id.tv_address));


            if (!TextUtils.isEmpty(polyLinePoints.get(mHashMap.get(marker)).getUserImageUrl())) {
                url = polyLinePoints.get(mHashMap.get(marker)).getUserImageUrl();

                Picasso.with(MapActivity.this)
                        .load(url)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(ivUserImage, new MarkerCallback(marker));

            } else {
                ivUserImage.setImageResource(R.mipmap.ic_launcher);
            }

            title = marker.getTitle();

            if (!TextUtils.isEmpty(title)) {
                tvUserName.setText(title);
            } else {
                tvUserName.setText("");
            }

            snippet = polyLinePoints.get(mHashMap.get(marker)).getAddress();
            if (!TextUtils.isEmpty(snippet)) {
                tvUserAddress.setText(snippet);
            } else {
                tvUserAddress.setText("");
            }

            return view;
        }
    }

    public class MarkerCallback implements Callback {
        Marker marker=null;

        MarkerCallback(Marker marker) {
            this.marker=marker;
        }

        @Override
        public void onError() {
            Log.e(getClass().getSimpleName(), "Error loading thumbnail!");
        }

        @Override
        public void onSuccess() {
            if (marker != null && marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
                marker.showInfoWindow();
            }
        }
    }


}
