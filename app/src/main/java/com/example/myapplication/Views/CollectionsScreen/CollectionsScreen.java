package com.example.myapplication.Views.CollectionsScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CollectionsScreen extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_collections_screen);

        List<Fragment> list = new ArrayList<>();
        list.add(new SetsSidePage());
        list.add(new RoutineSidePage());

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionScreenSlider(getSupportFragmentManager(), list);

        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);



    }
}