package com.example.myapplication.Views.CollectionsScreen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.Views.CollectionsScreen.RoutineCollectionScreen.RoutineSidePage;
import com.example.myapplication.Views.CollectionsScreen.SetsCollectionScreen.SetsSidePage;

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
            return "Lịch trình";
        }
        else return "Bài tập đơn";
    }

    public void deleteRoutine(int position)
    {
        for (Fragment f: fragementList)
        {
            if(f instanceof RoutineSidePage)
            {
                ((RoutineSidePage)f).deleteRoutine(position);
            }
        }
    }

    public void deleteSet(int position)
    {
        for (Fragment f: fragementList)
        {
            if(f instanceof SetsSidePage)
            {
                ((SetsSidePage)f).deleteSet(position);
            }
        }
    }

    public void listenToKeyChange(String s)
    {
        for (Fragment f: fragementList)
        {
            if(f instanceof RoutineSidePage)
            {
                ((RoutineSidePage)f).listenToKeyChange(s);
            }
            else if(f instanceof SetsSidePage)
            {
                ((SetsSidePage)f).listenToKeyChange(s);
            }
        }
    }
}
