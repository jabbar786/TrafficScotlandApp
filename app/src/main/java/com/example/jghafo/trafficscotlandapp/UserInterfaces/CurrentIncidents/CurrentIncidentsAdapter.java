package com.example.jghafo.trafficscotlandapp.UserInterfaces.CurrentIncidents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.jghafo.trafficscotlandapp.R;

import java.util.List;

//Name: Jabbar Ghafoor
//Student ID: S1514090

public class CurrentIncidentsAdapter extends RecyclerView.Adapter<CurrentIncidentsAdapter.IndicatotViewHolder> {

    Context context;
    List<RSSFeed> filterItems;
    AdapterListener listener;
    boolean on_attach=true;

    public CurrentIncidentsAdapter(Context context, List<RSSFeed> RSSFeeds, List<RSSFeed> filterItems, AdapterListener listener){
        this.context = context;
        this.filterItems = filterItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CurrentIncidentsAdapter.IndicatotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carview_layout, parent, false);
        return new IndicatotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentIncidentsAdapter.IndicatotViewHolder holder, int position) {
        holder.locationTV.setText(filterItems.get(position).getTitle());
        holder.dateTv.setText(filterItems.get(position).getPubDate());
        if (position % 2 == 0) {

        }
    }


    @Override
    public int getItemCount() {
        return filterItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }


    public class IndicatotViewHolder extends RecyclerView.ViewHolder{

        private TextView locationTV;
        private TextView dateTv;
        private ImageView playBtn;
        public IndicatotViewHolder(@NonNull View itemView) {
            super(itemView);
            locationTV = itemView.findViewById(R.id.markerText);
            dateTv = itemView.findViewById(R.id.dateInfo);
            playBtn = itemView.findViewById(R.id.carImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onIncidentItemClick(filterItems.get(getAdapterPosition()), getAdapterPosition());
                }
            });

        }
    }
}
