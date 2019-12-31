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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    boolean isRecording;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ArrayList<LatLng> points;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Driver");
    private Boolean mLocationPermissionsGranted = false;
    private static final float DEFAULT_ZOOM = 15f;

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
    private void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void getDeviceLocation(){

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                            //getRouteToMarker(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));

                        }else{
                            Toast.makeText(MapsActivity2.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
        }
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
            Bundle extras = getIntent().getExtras();
            String Location = extras.getString("Location");//goingwreturning
            myRef.child("Bus Lines").child("Going").child(Location).child("Line").setValue(points);


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
