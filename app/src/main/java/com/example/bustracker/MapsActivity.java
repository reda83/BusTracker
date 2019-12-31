package com.example.bustracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Button button;
    Button tripBut;
    boolean tripStarted ;
    private FusedLocationProviderClient client;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;
    Marker currentMarker = null;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Driver");
    double latit = 29.9993;
    double longit = 31.4985;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    Bundle extras ;
    String BusLine;
    String GoingOrReturning;
    ValueEventListener listner;
    boolean isEnded;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionsGranted = false;
    private static final float DEFAULT_ZOOM = 15f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        extras = getIntent().getExtras();
        BusLine = extras.getString("BusLine");
         GoingOrReturning = extras.getString("GoingOrReturning");
        account = GoogleSignIn.getLastSignedInAccount(this);
        tripBut=findViewById(R.id.star_end_trip);
        if(account == null){
            tripBut.setVisibility(View.VISIBLE);

        }
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFusedLocationProviderClient =LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        getLocationPermission();


        tripStarted = false;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .setHostedDomain("miuegypt.edu.eg")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity.this);

    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
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
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);

            //        DriverTrackMove();
            //        setDriverTrack();


//                    Polyline line = mMap.addPolyline(new PolylineOptions()
//                            .add(new LatLng(29.993058, 31.417643),
//                                    new LatLng(29.996336, 31.419788),
//                                    new LatLng(29.996425, 31.419915)
//                                    ,new LatLng(29.996588, 31.419944))
//                            .width(10)
//                            .color(Color.BLUE));

//        Polyline line = mMap.addPolyline(new PolylineOptions()
//                .add(new LatLng(29.993058, 31.417643), new LatLng(29.996336, 31.419788),new LatLng(29.996425, 31.419915) ,new LatLng(29.996588, 31.419944))
//                .width(10)
//                .color(Color.BLUE)); how to draw polylines

            //        Polyline line = mMap.addPolyline(new PolylineOptions()
            //                .add(new LatLng(29.993058, 31.417643), new LatLng(29.996336, 31.419788),new LatLng(29.996425, 31.419915) ,new LatLng(29.996588, 31.419944))
            //                .width(10)
            //                .color(Color.BLUE)); how to draw polylines

            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

            //calling set location every 3 seconds
            if(account==null){
//                final Handler ha=new Handler();
//                ha.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        setDriverTrack();
//                        ha.postDelayed(this, 3000);
//
//                    }
//                },3000);
                }
            else{final Handler ha=new Handler();
                ha.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DriverTrackMove();
                        ha.postDelayed(this, 3000);

                    }
                },3000);}



        }

    }

    public void setDriverTrack() {

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
        }


//        List<LatLng> line = new ArrayList<>();
//        line.add(new LatLng(29.993058, 31.417643));
//        line.add(new LatLng(29.996336, 31.419788));
//        line.add(new LatLng(29.996425, 31.419915));
//        line.add(new LatLng(29.996588, 31.419944));
//
//
//        myRef.child("Bus Lines").child(GoingOrReturning).child(BusLine).child("Line").setValue(line);


        listner =myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) { latit=location.getLatitude();
                            longit=location.getLongitude();
                            //String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Bundle extras = getIntent().getExtras();
                            String BusLine = extras.getString("BusLine");
                            String GoingOrReturning = extras.getString("GoingOrReturning");
                            myRef.child("Bus Lines").child(GoingOrReturning).child(BusLine).child("lat").setValue(latit);
                            myRef.child("Bus Lines").child(GoingOrReturning).child(BusLine).child("lon").setValue(longit);
                            //Toast.makeText(MapsActivity.this, "Lat: "+latit+" "+"Longit: "+longit, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
        });
    }
        public void DriverTrackMove()
        {
              myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Bundle extras = getIntent().getExtras();
                    String BusLine = extras.getString("BusLine");
                    String GoingOrReturning = extras.getString("GoingOrReturning");
                    Double lat = dataSnapshot.child("Bus Lines").child(GoingOrReturning).child(BusLine).child("lat").getValue(Double.class);
                    Double lon = dataSnapshot.child("Bus Lines").child(GoingOrReturning).child(BusLine).child("lon").getValue(Double.class);

                    Map<String,String> mymap = new HashMap<String, String>();
                    ArrayList<HashMap<String,Double>> retrivedlines;
                    retrivedlines =(ArrayList<HashMap<String,Double>>) dataSnapshot.child("Bus Lines").child("Going").child(BusLine).child("Line").getValue();
//                LatLng hi = retrivedlines.get(0);
//
                    Polyline line;
                    ArrayList<LatLng> DrawLine = new ArrayList<LatLng>();
                    if(!(retrivedlines==null)) {
                        for (int i = 0; i < retrivedlines.size(); i++) {
                            Double latLine = retrivedlines.get(i).get("latitude");
                            Double lonLine = retrivedlines.get(i).get("longitude");

                            DrawLine.add(new LatLng(latLine, lonLine));


                        }
                        line = mMap.addPolyline(new PolylineOptions()
                                .addAll(DrawLine)
                                .width(10)
                                .color(Color.BLUE));
                    }
//                    Toast.makeText(getApplicationContext(),""+ retrivedlines.get(0).get("latitude"),Toast.LENGTH_SHORT).show();

                    if (currentMarker != null) {
                        currentMarker.remove();
                        currentMarker = null;
                    }
                    if (currentMarker == null) {//exception aw ay 7aga
                        currentMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    private void signOut() {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Intent i = new Intent(this,MainActivity.class);
        if(account!=null){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });
            startActivity(i);

        }
        else {
            mFirebaseAuth.getInstance().signOut();
            startActivity(i);

        }
    }

    public void changeTripStatus(View view) {

        tripStarted = !tripStarted;
        final Handler ha=new Handler();
        Runnable obj = new Runnable() {
            @Override
            public void run() {
                if(tripStarted) {
                    setDriverTrack();
                    ha.postDelayed(this, 3000);
                }
                else{
//                    myRef.child("Bus Lines").child(GoingOrReturning).child(BusLine).child("lat").setValue("''");

                  }
            }
        };
        if(tripStarted){
            tripBut.setText("End Trip");
            myRef.child("Bus Lines").child(GoingOrReturning).child(BusLine).child("isStarted").setValue("true");

            if(account==null){

                ha.postDelayed(obj,100); }
            //start tracking


        }
        else{

            tripBut.setText("Start Trip");
            myRef.child("Bus Lines").child(GoingOrReturning).child(BusLine).child("isStarted").setValue("false");
            ha.removeCallbacks(obj);
            myRef.removeEventListener(listner);

            //stop tracking
        }
    }
}
