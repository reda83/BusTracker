package com.example.bustracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RoutesViewHolder> {
    private ArrayList<Route> mRouteList;
    private IOnRouteClickListener onItemClickListener;
    public boolean shouldShowBtn = false;

    public RouteAdapter(
            ArrayList<Route> RouteList,
            IOnRouteClickListener onItemClickListener) {
        mRouteList = RouteList;
        this.onItemClickListener = onItemClickListener;
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
        final Route currentRoute = mRouteList.get(position);
        holder.bind(currentRoute);
    }

    @Override
    public int getItemCount() {
        return mRouteList.size();
    }

    public void toggleShouldShowDeleteBtn() {
        this.shouldShowBtn = !this.shouldShowBtn;
        this.updateRemoveBtnVisibility();
    }

    private void updateRemoveBtnVisibility() {
        this.notifyDataSetChanged();
    }

    public class RoutesViewHolder extends RecyclerView.ViewHolder {
        public TextView mTime;
        public TextView mLocation;
        public TextView mLine;
        public ImageView mRemoveIv;

        public RoutesViewHolder(@NonNull View itemView) {
            super(itemView);
            mTime = itemView.findViewById(R.id.timeText);
            mLocation = itemView.findViewById(R.id.locationText);
            mLine = itemView.findViewById(R.id.lineText);
            mRemoveIv = itemView.findViewById(R.id.deleteIcon);
        }

        public void bind(final Route route) {
            this.mLine.setText(route.getLine());
            this.mTime.setText(route.getTime());
            this.mLocation.setText(route.getLocation());
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnClick(route);
                }
            });

            if (shouldShowBtn) {
                mRemoveIv.setVisibility(View.VISIBLE);
            } else {
                mRemoveIv.setVisibility(View.INVISIBLE);
            }
        }
    }
}
