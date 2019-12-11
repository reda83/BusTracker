package com.example.bustracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
//        bottomNav.setOnNavigationItemSelectedListener(navListener);


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

