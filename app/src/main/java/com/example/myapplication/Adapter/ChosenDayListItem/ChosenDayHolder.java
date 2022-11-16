package com.example.myapplication.Adapter.ChosenDayListItem;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ChosenDayHolder extends RecyclerView.ViewHolder {

    TextView txtDay;

    public ChosenDayHolder(@NonNull View itemView) {
        super(itemView);
        txtDay = itemView.findViewById(R.id.txtDay);
    }
}
