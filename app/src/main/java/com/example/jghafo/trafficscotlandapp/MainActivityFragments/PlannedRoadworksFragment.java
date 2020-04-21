package com.example.jghafo.trafficscotlandapp.MainActivityFragments;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.jghafo.trafficscotlandapp.UserInterfaces.PlannedRoadworks.PlannedRoadWorksAdapter;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.PlannedRoadworks.AdapterListener;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.PlannedRoadworks.RSSFeedResponse;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.PlannedRoadworks.PlannedRoadWorksXMLParser;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.PlannedRoadworks.RSSFeed;
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

//Name: Jabbar Ghafoor
//Student ID: S1514090

public class PlannedRoadworksFragment extends Fragment implements AdapterListener, RSSFeedResponse {

    private View view;
    private TextView error;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private PlannedRoadWorksAdapter plannedRoadWorksAdapter;
    private List<RSSFeed> RSSFeeds;
    private List<RSSFeed> searchFilter;

    private PlannedRoadWorksXMLParser plannedRoadWorksXMLParser;


    public PlannedRoadworksFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.planned_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = view.findViewById(R.id.plannedRecyclerview);
        error = view.findViewById(R.id.error);
        layoutManager = new LinearLayoutManager(getContext());
        RSSFeeds = new ArrayList<>();
        searchFilter = new ArrayList<>();

        recyclerView.setLayoutManager(layoutManager);
        plannedRoadWorksAdapter = new PlannedRoadWorksAdapter(getContext(), RSSFeeds, RSSFeeds, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(plannedRoadWorksAdapter);
        plannedRoadWorksXMLParser = new PlannedRoadWorksXMLParser();
        plannedRoadWorksXMLParser.response = this;
        plannedRoadWorksXMLParser.execute("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx");


    }

    @Override
    public void onPlannedRoadWorkItemClick(RSSFeed item, int pos) {

        Gson gson = new Gson();
        String jsonString = gson.toJson(RSSFeeds);
        Intent intent = new Intent(getActivity(), TabbedFragment.class);
        intent.putExtra("listItem", jsonString);
        intent.putExtra("pos", pos);
        intent.putExtra("plannedWork", "PlanedRoadWork");
        startActivity(intent);
    }


    private void searchInfo(final List<RSSFeed> itemList) {

        MainActivity.getInstance().action_search.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                List<RSSFeed> filteredList = new ArrayList<>();
                if (query.isEmpty()) {
                    filteredList = itemList;
                } else {

                    for (RSSFeed item : itemList) {

                        if (item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                }
                RSSFeeds.clear();
                RSSFeeds.addAll(filteredList);
                plannedRoadWorksAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                List<RSSFeed> filteredList = new ArrayList<>();

                if (newText.isEmpty()) {
                    filteredList = itemList;
                } else {

                    for (RSSFeed item : itemList) {
                        if (item.getTitle() != null) {
                            if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                                filteredList.add(item);
                            }
                        }
                    }
                }
                RSSFeeds.clear();
                RSSFeeds.addAll(filteredList);
                plannedRoadWorksAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            searchInfo(searchFilter);

        }

    }

    @Override
    public void processFinish(List<RSSFeed> output) {
        error.setVisibility(View.GONE);
        RSSFeeds.clear();
        RSSFeeds.addAll(output);
        searchFilter.addAll(output);
        plannedRoadWorksAdapter.notifyDataSetChanged();
    }
}
