package com.example.bustracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutesRecyclerView extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Driver");
     String location;
    Context context = this;
    ArrayList<Route> routesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_recycler_view);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setOnTabSelectedListener(tabListener);
        Fragment selectedFragment = new GoingFragment(context);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
        List<LatLng> line = new ArrayList<>();
        line.add(new LatLng(29.993058, 31.417643));
        line.add(new LatLng(29.996336, 31.419788));
        line.add(new LatLng(29.996425, 31.419915));
        line.add(new LatLng(29.996588, 31.419944));
        myRef.child("Bus Lines").child("Going").child("Sheraton").child("Line").setValue(line);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                List<LatLng> retrivedlines = new ArrayList<>();
//                retrivedlines= (List<LatLng>) dataSnapshot.child("Bus Lines").child("Going").child("Sheraton").child("Line").getValue(Map.class);
//                Toast.makeText(getApplicationContext(),""+retrivedlines.get(0).longitude+retrivedlines.get(0).latitude,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //the method below to get any information about the signed in user
//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//        if (acct != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();
//            Toast.makeText(this, "Person Name: "+personName+" Person Family Name: "+personFamilyName+" Email: "+personEmail, Toast.LENGTH_SHORT).show();
//
//        }
    }
    TabLayout.OnTabSelectedListener tabListener=
            new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    Fragment selectedFragment = null;

                    switch (tab.getPosition()){
                        case 0:
                            selectedFragment = new GoingFragment(context);
                            break;
                        case 1:
                            selectedFragment = new ReturningFragment(context);
                            break;
//                        case R.id.nav_search:
//                            selectedFragment = new SearchFragment();
//                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {


                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            };






//    public void BusLinesList()
//    {
//        mRecyclerView = findViewById(R.id.routesRecyclerView);
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        mAdapter = new RouteAdapter(routesList);
//
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);
//
//
//        mRecyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(context, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//                        Intent i = new Intent(getApplicationContext(),MapsActivity.class);
//                        startActivity(i);
//                    }
//
//                    @Override public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );
//
//    }
    }

