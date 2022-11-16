package com.example.myapplication.Views.CollectionsScreen.SetsCollectionScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class SetsSidePage extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.slide_page_set_pager,
                container,
                false
        );

        return rootView;
    }
    public  void listenToKeyChange(String s)
    {
//        System.out.println("Sets Side "+s);
    }
}
