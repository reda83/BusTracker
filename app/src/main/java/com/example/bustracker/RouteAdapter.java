package com.example.bustracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RoutesViewHolder> {
    private ArrayList<Route> mRouteList;

    public RouteAdapter(ArrayList<Route> RouteList) {
        mRouteList = RouteList;
    }

    @NonNull
    @Override
    public RoutesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_item, parent, false);
        RoutesViewHolder vh = new RoutesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesViewHolder holder, int position) {
        Route currentRoute = mRouteList.get(position);

        holder.mLine.setText(currentRoute.getLine());
        holder.mTime.setText(currentRoute.getTime());
        holder.mLocation.setText(currentRoute.getLocation());

    }

    @Override
    public int getItemCount() {
        return mRouteList.size();
    }

    public static class RoutesViewHolder extends RecyclerView.ViewHolder{
        public TextView mTime;
        public TextView mLocation;
        public TextView mLine;

        public RoutesViewHolder(@NonNull View itemView) {
            super(itemView);
            mTime = itemView.findViewById(R.id.timeText);
            mLocation = itemView.findViewById(R.id.locationText);
            mLine = itemView.findViewById(R.id.lineText);

        }
    }
}
