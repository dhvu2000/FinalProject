package com.example.myapplication.Views.CollectionsScreen.WorkOutProcessScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.R;

public class WorkOutProcessScreen1 extends AppCompatActivity {

    ProgressBar progressBar;
    ImageButton btnSkip, btnPauseContinue, btnStop;
    TextView txtHead, txtBtnPauseContinue, txtNextExerciseName, txtReps, txtTime;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_work_out_process_screen1);

        progressBar = findViewById(R.id.progressBar);
        btnSkip = findViewById(R.id.btnSkip);
        btnPauseContinue = findViewById(R.id.btnPauseContinue);
        btnStop = findViewById(R.id.btnStop);
        txtHead = findViewById(R.id.txtHead);
        txtBtnPauseContinue = findViewById(R.id.txtBtnPauseContinue);
        txtReps = findViewById(R.id.txtNextReps);
        txtTime = findViewById(R.id.txtNextTime);
        txtNextExerciseName = findViewById(R.id.txtNextExerciseName);
        img = findViewById(R.id.img);

        progressBar.setProgress(0);


    }
}