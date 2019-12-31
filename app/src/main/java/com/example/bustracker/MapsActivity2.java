package com.example.bustracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    boolean isRecording;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ArrayList<LatLng> points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        isRecording = false;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        points = new ArrayList<>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMyLocationEnabled(true);
//        final Handler ha=new Handler();
//        ha.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                trackLocation();
//                Log.d("TAG", "handler: ");
//                ha.postDelayed(this, 3000);
//
//            }
//        },1000);

    }

    private void trackLocation(){

                mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        double latit=location.getLatitude();
                        double longit=location.getLongitude();
                        Toast.makeText(getApplicationContext(),""+latit,Toast.LENGTH_SHORT);
                        Log.d("TAG", "onSuccess: "+latit);
                        points.add(new LatLng(latit,longit));
                        //String id=FirebaseAuth.getInstance().getCurrentUser().getUid();


                    }
                });
            }

    public void changeRecordStatus(View view) {
        isRecording = !isRecording;
        final Handler ha=new Handler();
        Button btn = findViewById(R.id.Recordtrip);
        if (isRecording){
            btn.setText("Stop Recording");
            ha.postDelayed(new Runnable() {
                @Override
                public void run() {
                    trackLocation();
                    Log.d("TAG", "handler: ");
                    ha.postDelayed(this, 300);

                }
            },1000);
        }
        else{
        btn.setVisibility(View.INVISIBLE);
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .addAll(points)
                .width(10)
                .color(Color.BLUE));
    }}


//    private void startLocationUpdates() {
//
//        mFusedLocationProviderClient.requestLocationUpdates(locationRequest,
//                locationCallback,
//                Looper.getMainLooper());
//    }
}
