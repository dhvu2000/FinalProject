package com.example.myapplication.Views.CollectionsScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CollectionsPageFragment extends Fragment {


    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    public CollectionsPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collections_page, container, false);
        List<Fragment> list = new ArrayList<>();
        list.add(new SetsSidePage());
        list.add(new RoutineSidePage());

        viewPager = view.findViewById(R.id.pager);
        pagerAdapter = new CollectionScreenSlider(getParentFragmentManager(), list);

        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void showNotice(String s)
    {
        if(getActivity()!= null)
            Toast.makeText(getActivity(),s,Toast.LENGTH_LONG);
    }
}