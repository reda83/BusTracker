package com.example.bustracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

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
//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
//        if (acct != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();
//        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> location = (Map<String, Object>) dataSnapshot.child("Bus Lines").getValue();
                List<String> l = new ArrayList<>(location.keySet());



//                Log.d("location ", "onDataChange: "+l.get(0));
//                routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//                routesList.add(new Route("Sheraton","7:30 AM", "Al Seddik Mosque"));
//                routesList.add(new Route("5th Settelment","7:30 AM", "Nth 90 St."));
                for(int i=0;i<l.size();i++) {
                    routesList.add(new Route(l.get(i), dataSnapshot.child("Bus Lines").child(l.get(i)).child("Start Time").getValue(String.class), dataSnapshot.child("Bus Lines").child(l.get(i)).child("Start From").getValue(String.class)));
                }
                BusLinesList();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));
//        routesList.add(new Route("Madinet Nasr","7:30 AM", "El-Tayran St."));



    }

    public void BusLinesList()
    {
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
