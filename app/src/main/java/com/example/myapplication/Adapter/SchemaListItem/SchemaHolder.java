package com.example.myapplication.Adapter.SchemaListItem;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class SchemaHolder extends RecyclerView.ViewHolder {


    TextView txtDate, txtWeight, txtHeight;
    Button btnDelete;
    public SchemaHolder(@NonNull View itemView) {
        super(itemView);
        txtDate = itemView.findViewById(R.id.txtDate);
        txtHeight = itemView.findViewById(R.id.txtHeight);
        txtWeight = itemView.findViewById(R.id.txtWeight);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }
}
