package com.example.myapplication.Views.CollectionsScreen.WorkOutProcessScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Supporter.TimeFormatter;

public class WorkOutProcessEndScreen extends AppCompatActivity {

    TextView txtFinish, txtCongrats, txtTime, txtCalories;
    Button btnDone;
    long time;
    double calories;
    boolean done;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_work_out_process_end);
        txtFinish = findViewById(R.id.txtFinish);
        txtCongrats = findViewById(R.id.txtCongrats);
        txtCalories = findViewById(R.id.txtCalories);
        txtTime = findViewById(R.id.txtTime);
        btnDone = findViewById(R.id.btnDone);


        time = getIntent().getLongExtra("time", 0);
        calories = getIntent().getDoubleExtra("calories", 0);
        done = getIntent().getBooleanExtra("done", false);

        String result = String.format("%.1f", calories);
        txtTime.setText(TimeFormatter.FormatToHourTime(time));
        txtCalories.setText(result+ " cal");

        if(calories == 0)
        {
            txtCongrats.setVisibility(View.GONE);
            txtFinish.setVisibility(View.GONE);
        }

        if(!done)
        {
            txtCongrats.setVisibility(View.GONE);
        }

        btnDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(done)
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.welldone);
            mediaPlayer.start();
        }
        else
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.bell);
            mediaPlayer.start();
        }

    }
}