package com.example.myapplication.Adapter.ExerciseListItem;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ExerciseHolder extends RecyclerView.ViewHolder {

    ImageView img;
    TextView txtName, txtIntroduction;
    LinearLayout cardView;


    public ExerciseHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.img);
        txtName = itemView.findViewById(R.id.txtName);
        txtIntroduction = itemView.findViewById(R.id.txtIntroduction);
        cardView = itemView.findViewById(R.id.exerciseCardView);

    }
}
