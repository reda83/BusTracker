package com.example.bustracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
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

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Button button;
    Button tripBut;
    boolean tripStarted ;
    private FusedLocationProviderClient client;
    private GoogleMap mMap;
    Marker currentMarker = null;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Driver");
    double latit = 29.9993;
    double longit = 31.4985;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        account = GoogleSignIn.getLastSignedInAccount(this);
        tripBut=findViewById(R.id.star_end_trip);
        if(account == null){
            tripBut.setVisibility(View.VISIBLE);

        }
        mFirebaseAuth = FirebaseAuth.getInstance();
        client=LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button = findViewById(R.id.btn1);
        tripStarted = false;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .setHostedDomain("miuegypt.edu.eg")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
//        MarkerOptions marker = new MarkerOptions().position(new LatLng(latit, longit)).title("Hello Maps");
//        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus));
//        googleMap.addMarker(marker); how to add customized marker

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return; //el permissions
        }
        mMap.setMyLocationEnabled(true);
//        DriverTrackMove();
//        setDriverTrack();

//        Polyline line = mMap.addPolyline(new PolylineOptions()
//                .add(new LatLng(29.993058, 31.417643), new LatLng(29.996336, 31.419788),new LatLng(29.996425, 31.419915) ,new LatLng(29.996588, 31.419944))
//                .width(10)
//                .color(Color.BLUE)); how to draw polylines


        //calling set location every 3 seconds
        if(account==null){
            final Handler ha=new Handler();
            ha.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setDriverTrack();
                    ha.postDelayed(this, 3000);

                }
            },3000);}
        else{final Handler ha=new Handler();
            ha.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DriverTrackMove();
                    ha.postDelayed(this, 3000);

                }
            },3000);}


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Polyline line = mMap.addPolyline(new PolylineOptions()
//                        .add(new LatLng(29.993058, 31.417643), new LatLng(29.996336, 31.419788),new LatLng(29.996425, 31.419915) ,new LatLng(29.996588, 31.419944))
//                        .width(10)
//                        .color(Color.BLUE));
                signOut();
//                DriverTrackMove();

            }
        });

        }

        public void setDriverTrack()
        {

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

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            latit=location.getLatitude();
                            longit=location.getLongitude();
        //                    String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Bundle extras = getIntent().getExtras();
                            String BusLine = extras.getString("BusLine");
                            String GoingOrReturning = extras.getString("GoingOrReturning");
                            myRef.child("Bus Lines").child(GoingOrReturning).child(BusLine).child("lat").setValue(latit);
                            myRef.child("Bus Lines").child(GoingOrReturning).child(BusLine).child("lon").setValue(longit);

//                            Toast.makeText(MapsActivity.this, "Lat: "+latit+" "+"Longit: "+longit, Toast.LENGTH_SHORT).show();

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
                    Double lat= dataSnapshot.child("Bus Lines").child(GoingOrReturning).child(BusLine).child("lat").getValue(Double.class);
                    Double lon= dataSnapshot.child("Bus Lines").child(GoingOrReturning).child(BusLine).child("lon").getValue(Double.class);

                    if(currentMarker!=null)
                    {
                        currentMarker.remove();
                        currentMarker=null;
                    }
                    if(currentMarker==null)
                    {
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
        if(account!=null){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });
        }
        else {
            mFirebaseAuth.getInstance().signOut();
        }
    }

    public void changeTripStatus(View view) {
        tripStarted = !tripStarted;

        if(tripStarted){
            tripBut.setText("End Trip");
            //start tracking
        }
        else{
            tripBut.setText("Start Trip");
            //stop tracking
        }
    }
}
