package com.example.myapplication.Views.CollectionsScreen.WorkOutProcessScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class WorkOutProcessEnd extends AppCompatActivity {

    TextView txtFinish, txtCongrats, txtTime, txtCalories;
    Button btnDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_out_process_end);
        txtFinish = findViewById(R.id.txtFinish);
        txtCongrats = findViewById(R.id.txtCongrats);
        txtCalories = findViewById(R.id.txtCalories);
        txtTime = findViewById(R.id.txtTime);
        btnDone = findViewById(R.id.btnDone);



    }

}