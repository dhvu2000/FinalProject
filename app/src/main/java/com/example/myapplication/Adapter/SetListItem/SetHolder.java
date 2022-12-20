package com.example.myapplication.Adapter.SetListItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class SetHolder extends RecyclerView.ViewHolder {

    TextView txtName;
    TextView txtExerciseNum;
    LinearLayout cardView;
    ImageView img;

    public SetHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txtName);
        txtExerciseNum = itemView.findViewById(R.id.txtExerciseNum);
        cardView = itemView.findViewById(R.id.setCardView);
        img = itemView.findViewById(R.id.img);
    }
}
