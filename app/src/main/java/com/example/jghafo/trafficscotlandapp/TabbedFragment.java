package com.example.jghafo.trafficscotlandapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.jghafo.trafficscotlandapp.FragmentAdapters.TabbedFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

//Name: Jabbar Ghafoor
//Student ID: S1514090

public class TabbedFragment extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabbedFragmentAdapter tabbedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinfo);


        Toolbar toolbar = findViewById(R.id.infoToobar);

        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.infoTab);
        viewPager = findViewById(R.id.infoViewPager);
        tabbedAdapter = new TabbedFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabbedAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}
