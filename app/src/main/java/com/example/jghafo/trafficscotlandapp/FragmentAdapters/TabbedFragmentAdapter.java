package com.example.jghafo.trafficscotlandapp.FragmentAdapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.jghafo.trafficscotlandapp.MoreInfoFragments.DetailsFragment;
import com.example.jghafo.trafficscotlandapp.MoreInfoFragments.GoogleMapsApiFragment;

//Name: Jabbar Ghafoor
//Student ID: S1514090

public class TabbedFragmentAdapter extends FragmentPagerAdapter {

    public TabbedFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new DetailsFragment();
            case 1:
                return new GoogleMapsApiFragment();
            default:
                return null;
        }
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "ADDITIONAL INFO";
            case 1:
                return "MAPS";
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return 2;
    }


}
