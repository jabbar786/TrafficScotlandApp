package com.example.jghafo.trafficscotlandapp.MainActivityFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jghafo.trafficscotlandapp.TabbedFragment;
import com.example.jghafo.trafficscotlandapp.MainActivity;
import com.example.jghafo.trafficscotlandapp.R;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.CurrentRoadworks.CurrentRoadWorksAdapter;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.CurrentRoadworks.AdapterListener;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.CurrentRoadworks.RSSFeedResponse;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.CurrentRoadworks.RoadWorksXMLParser;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.CurrentRoadworks.RSSFeed;
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

//Name: Jabbar Ghafoor
//Student ID: S1514090

public class CurrentRoadworksFragment extends Fragment implements AdapterListener, RSSFeedResponse {

    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CurrentRoadWorksAdapter currentRoadWorksAdapter;
    private TextView error;
    private List<RSSFeed> RSSFeedList;
    private List<RSSFeed> searchFilter;
    RoadWorksXMLParser roadWorksXMLParser;

    public CurrentRoadworksFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.roadworks_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = view.findViewById(R.id.currentRoadworksRecyclerview);
        error = view.findViewById(R.id.error);
        layoutManager = new LinearLayoutManager(getContext());
        RSSFeedList =new ArrayList<>();
        searchFilter=new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        currentRoadWorksAdapter = new CurrentRoadWorksAdapter(getContext(), RSSFeedList, RSSFeedList, CurrentRoadworksFragment.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(currentRoadWorksAdapter);

        roadWorksXMLParser = new RoadWorksXMLParser();
        roadWorksXMLParser.response = this;
        roadWorksXMLParser.execute("https://trafficscotland.org/rss/feeds/roadworks.aspx");

    }

    @Override
    public void onRoadWorksItemClickListener(RSSFeed item, int pos) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(RSSFeedList);
        Intent intent = new Intent(getActivity(), TabbedFragment.class);
        intent.putExtra("listItem", jsonString);
        intent.putExtra("pos", pos);
        intent.putExtra("plannedWork", "PlanedRoadWork");
        startActivity(intent);
    }

    private void searchInfo(final List<RSSFeed> itemList){

        MainActivity.getInstance().action_search.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<RSSFeed> filteredList = new ArrayList<>();
                if (query.isEmpty()) {
                    filteredList = itemList;
                } else {

                    for (RSSFeed item: itemList) {
                        if (item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                }
                RSSFeedList.clear();
                RSSFeedList.addAll(filteredList);
                currentRoadWorksAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                List<RSSFeed> filteredList = new ArrayList<>();
                Log.d("SEARCH_DATA", "onQueryTextSubmit: "+newText);
                if (newText.isEmpty()) {
                    filteredList = itemList;
                } else {

                    for (RSSFeed item: itemList) {
                        if(item.getTitle() != null){
                            if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                                filteredList.add(item);
                            }
                        }
                    }
                }

                RSSFeedList.clear();
                RSSFeedList.addAll(filteredList);
                currentRoadWorksAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible){
            searchInfo(searchFilter);
        }
    }

    @Override
    public void processFinish(List<RSSFeed> output) {
        error.setVisibility(View.GONE);
        RSSFeedList.clear();
        RSSFeedList.addAll(output);
        searchFilter.addAll(output);
        currentRoadWorksAdapter.notifyDataSetChanged();
    }

}

