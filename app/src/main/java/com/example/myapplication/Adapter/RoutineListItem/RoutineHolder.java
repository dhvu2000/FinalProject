package com.example.myapplication.Adapter.RoutineListItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class RoutineHolder extends RecyclerView.ViewHolder{

    ImageView img;
    TextView txtName, txtDaysNum;
    LinearLayout cardView;

    public RoutineHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.img);
        txtName = itemView.findViewById(R.id.txtName);
        txtDaysNum = itemView.findViewById(R.id.txtDaysNum);
        cardView = itemView.findViewById(R.id.routineCardView);
    }
}
