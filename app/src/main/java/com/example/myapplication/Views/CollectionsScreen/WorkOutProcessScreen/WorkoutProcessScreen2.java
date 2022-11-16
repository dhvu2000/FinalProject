package com.example.myapplication.Views.CollectionsScreen.WorkOutProcessScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.R;

public class WorkoutProcessScreen2 extends AppCompatActivity {


    ImageButton btnSkip, btnPauseContinue, btnStop;
    TextView txtName, txtBtnPauseContinue, txtReps, txtTime;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_process_screen2);

        txtName = findViewById(R.id.txtName);
        btnSkip = findViewById(R.id.btnSkip);
        btnPauseContinue = findViewById(R.id.btnPauseContinue);
        btnStop = findViewById(R.id.btnStop);
        txtBtnPauseContinue = findViewById(R.id.txtBtnPauseContinue);
        txtReps = findViewById(R.id.txtNextReps);
        txtTime = findViewById(R.id.txtNextTime);
        img = findViewById(R.id.img);
    }
}