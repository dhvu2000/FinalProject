package com.example.myapplication.Adapter.FindAndAddExerciseItem;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class FindAndAddExerciseHolder extends RecyclerView.ViewHolder {


    TextView txtName;
    Button btnAdd, btnDetail;

    public FindAndAddExerciseHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txtName);
        btnAdd = itemView.findViewById(R.id.btnAdd);
        btnDetail = itemView.findViewById(R.id.btnDetail);
    }
}
