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
        line.add(new LatLng(30.1753,31.4731));
        line.add(new LatLng(30.1745,31.4732));
        line.add(new LatLng(30.1733,31.4740));
        line.add(new LatLng(30.1716,31.4751));
        line.add(new LatLng(30.1698,31.4758));
        line.add(new LatLng(30.1716,31.4751));
        line.add(new LatLng(30.1673,31.4767));
        line.add(new LatLng(30.1658,31.4767));
        line.add(new LatLng(30.1655,31.4770));
        line.add(new LatLng(30.1656,31.4774));
        line.add(new LatLng(30.1666,31.4775));
        line.add(new LatLng(30.1685,31.4767));
        line.add(new LatLng(30.1705,31.4761));
        line.add(new LatLng(30.1722,31.4819));
        line.add(new LatLng(30.1727,31.4851));
        line.add(new LatLng(30.1727,31.4877));
        line.add(new LatLng(30.1716,31.4892));
        line.add(new LatLng(30.1710,31.4895));
        line.add(new LatLng(30.1668,31.4897));
        line.add(new LatLng(30.1662,31.4892));
        line.add(new LatLng(30.1663,31.4873));
        line.add(new LatLng(30.1663,31.4860));
        line.add(new LatLng(30.1655,31.4854));
        line.add(new LatLng(30.1648,31.4857));
        line.add(new LatLng(30.1647,31.4865));
        line.add(new LatLng(30.165251,31.488879));
        line.add(new LatLng(30.1667,31.4980));
        line.add(new LatLng(30.1665,31.5001));
        line.add(new LatLng(30.1674,31.5011));
        line.add(new LatLng(30.1681,31.5000));
        line.add(new LatLng(30.1672,31.4978));
        line.add(new LatLng(30.1666,31.4923));



        myRef.child("Bus Lines").child("Going").child("Obour").child("Line").setValue(line);

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

