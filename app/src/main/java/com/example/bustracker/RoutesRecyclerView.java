package com.example.bustracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;

public class RoutesRecyclerView extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_recycler_view);
        Context context = this;
//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
//        if (acct != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();
//        }
        ArrayList<Route> routesList = new ArrayList<>();
        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
        routesList.add(new Route("Sheraton","7:30 AM", "Al Seddik Mosque"));
        routesList.add(new Route("5th Settelment","7:30 AM", "Nth 90 St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));


        mRecyclerView = findViewById(R.id.routesRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RouteAdapter(routesList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent i = new Intent(getApplicationContext(),MapsActivity.class);
                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }
}
