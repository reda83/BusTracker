package com.example.bustracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReturningFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Driver");
    String location;
//    Context context = this;
    ArrayList<Route> routesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_going,container,false);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> location = (Map<String, Object>) dataSnapshot.child("Bus Lines").getValue();
                List<String> l = new ArrayList<>(location.keySet());


                for(int i=0;i<l.size();i++) {
                    routesList.add(new Route(l.get(i), dataSnapshot.child("Bus Lines").child(l.get(i)).child("Start Time").getValue(String.class), dataSnapshot.child("Bus Lines").child(l.get(i)).child("Start From").getValue(String.class)));
                }
                mRecyclerView = rootView.findViewById(R.id.routesRecyclerView);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(rootView.getContext());
                mAdapter = new RouteAdapter(routesList);

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);


//                mRecyclerView.addOnItemTouchListener(
//                        new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
//                            @Override public void onItemClick(View view, int position) {
//                                Intent i = new Intent(,MapsActivity.class);
//                                startActivity(i);
//                            }
//
//                            @Override public void onLongItemClick(View view, int position) {
//                                // do whatever
//                            }
//                        })
//                );


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

                return rootView;
    }

}
