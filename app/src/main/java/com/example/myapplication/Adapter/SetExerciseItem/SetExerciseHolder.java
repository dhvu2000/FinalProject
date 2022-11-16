package com.example.myapplication.Adapter.SetExerciseItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class SetExerciseHolder extends RecyclerView.ViewHolder {

     ImageView img;
     TextView txtName, txtTime, txtRep;
     LinearLayout cardView;
    public SetExerciseHolder(@NonNull View itemView) {
        super(itemView);

        img = itemView.findViewById(R.id.img);
        cardView = itemView.findViewById(R.id.cardView);
        txtName = itemView.findViewById(R.id.txtName);
        txtTime = itemView.findViewById(R.id.txtTime);
        txtRep = itemView.findViewById(R.id.txtRep);
    }
}
