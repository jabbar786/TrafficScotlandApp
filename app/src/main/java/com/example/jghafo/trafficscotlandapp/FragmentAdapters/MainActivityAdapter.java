package com.example.jghafo.trafficscotlandapp.FragmentAdapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.jghafo.trafficscotlandapp.MainActivityFragments.CurrentIncidentsFragment;
import com.example.jghafo.trafficscotlandapp.MainActivityFragments.PlannedRoadworksFragment;
import com.example.jghafo.trafficscotlandapp.MainActivityFragments.CurrentRoadworksFragment;

//Name: Jabbar Ghafoor
//Student ID: S1514090

public class MainActivityAdapter extends FragmentPagerAdapter {

    public static final String TITLE_INCIDENTS = "Current Incidents";
    public static final String TITLE_CROADWORKS = "Current Roadworks";
    public static final String TITLE_PLANNED = "Planned Roadworks";


    public MainActivityAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new CurrentIncidentsFragment();
            case 1:
                return new CurrentRoadworksFragment();
            case 2:
                return new PlannedRoadworksFragment();

            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return TITLE_INCIDENTS;
            case 1:
                return TITLE_CROADWORKS;
            case 2:
                return TITLE_PLANNED;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
