package com.example.myapplication.Adapter.RecordListItem;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class RecordHolder extends RecyclerView.ViewHolder {

    TextView txtDateTime, txtSpentTime, txtCalories, txtSetName;

    public RecordHolder(@NonNull View itemView) {
        super(itemView);
        txtDateTime = itemView.findViewById(R.id.txtDayTime);
        txtSpentTime = itemView.findViewById(R.id.txtSpentTime);
        txtCalories = itemView.findViewById(R.id.txtCalories);
        txtSetName = itemView.findViewById(R.id.txtSetName);
    }
}
