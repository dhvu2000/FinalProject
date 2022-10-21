package com.example.myapplication.Views.ExerciseScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.Model.WorkOutUnit.Exercise;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

public class DetailExerciseDialog extends DialogFragment {

    private Exercise exercise;
    private ImageView img;
    private TextView txtName;
    private TextView txtIntroduction;
    private TextView txtGuide;

    public DetailExerciseDialog(Exercise exercise)
    {
        this.exercise = exercise;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.detail_exercise_dialog,container,false);

        img = view.findViewById(R.id.img);
        txtName = view.findViewById(R.id.txtName);
        txtIntroduction = view.findViewById(R.id.txtIntroduction);
        txtGuide = view.findViewById(R.id.txtGuide);
        System.out.println("Exercise: "+ exercise);
        if(exercise.getImg()!=null && !exercise.getImg().isEmpty())
        {
            Picasso.get().load(exercise.getImg()).into(img);
        }else
        {
            Picasso.get().load(R.drawable.add_image).into(img);
        }
        if (exercise.getName()!=null)  txtName.setText(exercise.getName());
        if (exercise.getIntroduction()!=null)  txtIntroduction.setText(exercise.getIntroduction());
        if (exercise.getGuideline()!=null)  txtGuide.setText(exercise.getGuideline());

        return view;
    }
}