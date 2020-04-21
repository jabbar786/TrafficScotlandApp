package com.example.jghafo.trafficscotlandapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.jghafo.trafficscotlandapp.FragmentAdapters.MainActivityAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.Objects;

//Name: Jabbar Ghafoor
//Student ID: S1514090

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MainActivityAdapter mainActivityAdapter;
    public MaterialSearchView action_search;

    public static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        instance = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        action_search = (MaterialSearchView) findViewById(R.id.action_search);

        tabLayout = findViewById(R.id.mainTab);
        viewPager = findViewById(R.id.mainViewPager);
        mainActivityAdapter = new MainActivityAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainActivityAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());


        View currentIncidents = LayoutInflater.from(this).inflate(R.layout.currentincidents_view, null);
        currentIncidents.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        View currentRoadworks = LayoutInflater.from(this).inflate(R.layout.currentroadworks_view, null);
        currentRoadworks.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        View plannedRoadworks = LayoutInflater.from(this).inflate(R.layout.plannedroadworks_view, null);
        plannedRoadworks.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(currentIncidents);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(currentRoadworks);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(plannedRoadworks);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_mainmenu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        action_search.setMenuItem(search);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (action_search.isSearchOpen()) {
            action_search.closeSearch();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
