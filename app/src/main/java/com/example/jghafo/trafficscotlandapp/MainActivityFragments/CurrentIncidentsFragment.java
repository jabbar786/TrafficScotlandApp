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
import com.example.jghafo.trafficscotlandapp.UserInterfaces.CurrentIncidents.CurrentIncidentsAdapter;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.CurrentIncidents.AdapterListener;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.CurrentIncidents.RSSFeedResponse;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.CurrentIncidents.CurrentIncidentsXMLParser;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.CurrentIncidents.RSSFeed;
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

//Name: Jabbar Ghafoor
//Student ID: S1514090

public class CurrentIncidentsFragment extends Fragment implements AdapterListener, RSSFeedResponse {

    View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CurrentIncidentsAdapter currentIncidentsAdapter;
    private TextView error;
    private List<RSSFeed> RSSFeedList;
    private List<RSSFeed> searchFilter;

    CurrentIncidentsXMLParser currentIncidentsXMLParser;

    public CurrentIncidentsFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.incidents_fragment, container, false);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        error = view.findViewById(R.id.error);
        recyclerView = view.findViewById(R.id.currentIncidentsRecyclerview);
        layoutManager = new LinearLayoutManager(getContext());
        RSSFeedList = new ArrayList<>();
        searchFilter = new ArrayList<>();

        recyclerView.setLayoutManager(layoutManager);
        currentIncidentsAdapter = new CurrentIncidentsAdapter(getContext(), RSSFeedList, RSSFeedList, CurrentIncidentsFragment.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(currentIncidentsAdapter);
        currentIncidentsXMLParser = new CurrentIncidentsXMLParser();
        currentIncidentsXMLParser.response = this;
        currentIncidentsXMLParser.execute("https://trafficscotland.org/rss/feeds/currentincidents.aspx");

    }

    @Override
    public void onIncidentItemClick(RSSFeed RSSFeed, int pos) {

        Gson gson = new Gson();
        String jsonString = gson.toJson(RSSFeedList);
        Intent intent = new Intent(getActivity(), TabbedFragment.class);
        intent.putExtra("listItem", jsonString);
        intent.putExtra("pos", pos);
        intent.putExtra("incidents", "Incident");
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
                RSSFeedList.clear();
                RSSFeedList.addAll(filteredList);
                currentIncidentsAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<RSSFeed> filteredList = new ArrayList<>();
                if (newText.isEmpty() || newText.equals(" ")) {
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
                RSSFeedList.clear();
                RSSFeedList.addAll(filteredList);
                currentIncidentsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            if (RSSFeedList != null)
                searchInfo(searchFilter);
        }
    }

    @Override
    public void processFinish(List<RSSFeed> output) {
        error.setVisibility(View.GONE);
        RSSFeedList.clear();
        RSSFeedList.addAll(output);
        searchFilter.addAll(output);
        currentIncidentsAdapter.notifyDataSetChanged();
    }
}


