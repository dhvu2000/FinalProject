package com.example.myapplication.CollectionsScreen;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

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
