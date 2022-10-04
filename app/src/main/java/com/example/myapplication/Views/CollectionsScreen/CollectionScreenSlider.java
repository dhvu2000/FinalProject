package com.example.myapplication.Views.CollectionsScreen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class CollectionScreenSlider extends FragmentStatePagerAdapter {

    private List<Fragment> fragementList;

    public CollectionScreenSlider(FragmentManager fm, List<Fragment> fragementList)
    {
        super(fm);
        this.fragementList = fragementList;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragementList.get(position);
    }

    @Override
    public int getCount() {
        return fragementList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
        {
            return "SETS";
        }
        else return "ROUTINE";
    }
}
