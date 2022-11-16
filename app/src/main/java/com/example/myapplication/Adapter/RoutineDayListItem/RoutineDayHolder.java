package com.example.myapplication.Adapter.RoutineDayListItem;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class RoutineDayHolder extends RecyclerView.ViewHolder {

    TextView txtDay;
    TextView txtExerciseNum;
    LinearLayout cardView;

    public RoutineDayHolder(@NonNull View itemView) {
        super(itemView);
        txtDay = itemView.findViewById(R.id.txtDay);
        txtExerciseNum = itemView.findViewById(R.id.txtExerciseNum);
        cardView = itemView.findViewById(R.id.cardView);
    }
}
