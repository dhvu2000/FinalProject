package com.example.myapplication.Views.CollectionsScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.R;
import com.example.myapplication.Views.CollectionsScreen.RoutineCollectionScreen.RoutineSidePage;
import com.example.myapplication.Views.CollectionsScreen.SetsCollectionScreen.SetsSidePage;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CollectionsPageFragment extends Fragment {

    EditText txtSearch;
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

        txtSearch = view.findViewById(R.id.txtSearch);
        viewPager = view.findViewById(R.id.pager);
        pagerAdapter = new CollectionScreenSlider(getParentFragmentManager(), list);
        viewPager.setAdapter(pagerAdapter);

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(txtSearch != null)
                {
                    String key = txtSearch.getText().toString();
                    ((CollectionScreenSlider)pagerAdapter).listenToKeyChange(key);
                }

            }
        });

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    public void deleteRoutine(int position)
    {
        ((CollectionScreenSlider)pagerAdapter).deleteRoutine(position);
    }

    private void showNotice(String s)
    {
        if(getActivity()!= null)
            Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }
}