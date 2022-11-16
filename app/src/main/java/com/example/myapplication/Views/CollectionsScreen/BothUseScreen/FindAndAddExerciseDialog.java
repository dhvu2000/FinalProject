package com.example.myapplication.Views.CollectionsScreen.BothUseScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.FindAndAddExerciseItem.FindAndAddExerciseAdapter;
import com.example.myapplication.Model.WorkOutUnit.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.Supporter.SharePreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;

public class FindAndAddExerciseDialog extends DialogFragment {

    ArrayList<Exercise> exercises = new ArrayList<>();
    EditText txtSearch;
    Button btnAdd;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View addSetExerciseDialog =  inflater.inflate(R.layout.dialog_find_and_add_exercise,container,false);
        txtSearch = addSetExerciseDialog.findViewById(R.id.txtSearch);
        btnAdd = addSetExerciseDialog.findViewById(R.id.btnAdd);
        recyclerView = addSetExerciseDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        populateListView();
        return addSetExerciseDialog;
    }

    private void populateListView()
    {
        Exercise[] data = (Exercise[]) new SharePreferenceManager(getActivity()).getObject("Exercises", Exercise[].class);
        if(data != null && data.length != 0) exercises = new ArrayList<>(Arrays.asList(data));
        FindAndAddExerciseAdapter adapter = new FindAndAddExerciseAdapter(exercises, getActivity());
        recyclerView.setAdapter(adapter);
    }
}
