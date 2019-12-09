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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Button button;
    Button button2;

    private FusedLocationProviderClient client;
    private GoogleMap mMap;
    Marker currentMarker = null;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Driver");
    double latit = 29.9993;
    double longit = 31.4985;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mFirebaseAuth = FirebaseAuth.getInstance();
        client=LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);


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

//        Polyline line = mMap.addPolyline(new PolylineOptions()
//                .add(new LatLng(29.993058, 31.417643), new LatLng(29.996336, 31.419788),new LatLng(29.996425, 31.419915) ,new LatLng(29.996588, 31.419944))
//                .width(10)
//                .color(Color.BLUE)); how to draw polylines


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Polyline line = mMap.addPolyline(new PolylineOptions()
//                        .add(new LatLng(29.993058, 31.417643), new LatLng(29.996336, 31.419788),new LatLng(29.996425, 31.419915) ,new LatLng(29.996588, 31.419944))
//                        .width(10)
//                        .color(Color.BLUE));
                DriverTrackMove();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setDriverTrack();

//                FirebaseAuth.getInstance().signOut();
//                String wwh=FirebaseAuth.getInstance().getCurrentUser().getUid();
//                String id=myRef.push().getKey();
//                myRef.child(id).child("FullName").setValue("Reda");
//                myRef.child(id).child("Unique ID").setValue(id);
//                myRef.child(id).child("Email").setValue("Test2@test.com");
//                myRef.child(id).child("Password").setValue("123abc");
//                Toast.makeText(getApplicationContext(), wwh, Toast.LENGTH_SHORT).show();


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

            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    latit=location.getLatitude();
                    longit=location.getLongitude();
                    String id=FirebaseAuth.getInstance().getCurrentUser().getUid();

                    myRef.child("Bus Lines").child("Nasr City").child("lat").setValue(latit);
                    myRef.child("Bus Lines").child("Nasr City").child("lon").setValue(longit);

                    Toast.makeText(MapsActivity.this, "Lat: "+latit+" "+"Longit: "+longit, Toast.LENGTH_SHORT).show();

                }
            });

        }
        public void DriverTrackMove()
        {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();



                    //String idDriver=FirebaseAuth.getInstance().getCurrentUser().getUid();id driver
                    String DriverId=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String BusLine= dataSnapshot.child(DriverId).child("Bus Lines").getValue(String.class);

                    String fullname= dataSnapshot.child(DriverId).child("FullName").getValue(String.class);
                    Double lat= dataSnapshot.child("Bus Lines").child(BusLine).child("lat").getValue(Double.class);
                    Double lon= dataSnapshot.child("Bus Lines").child(BusLine).child("lon").getValue(Double.class);
//                        Double bagrb= dataSnapshot.child("Name").child("long").ge;

                    if(currentMarker!=null)
                    {
                        currentMarker.remove();
                        currentMarker=null;
                    }
                    if(currentMarker==null)
                    {
                        currentMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
                    }


                    String value = dataSnapshot.toString();

                    Toast.makeText(getApplicationContext(), fullname, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
}
