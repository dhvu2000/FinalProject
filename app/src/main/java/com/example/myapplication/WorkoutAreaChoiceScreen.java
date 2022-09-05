package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class WorkoutAreaChoiceScreen extends AppCompatActivity {

    ToggleButton btnUpperChoice;
    ToggleButton btnMiddleChoice;
    ToggleButton btnLowerChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_workout_area_choice_screen);

        btnUpperChoice = findViewById(R.id.btnUpperChoice);
        btnMiddleChoice = findViewById(R.id.btnAbsCoreChoice);
        btnLowerChoice = findViewById(R.id.btnLowerBodChoice);

        btnUpperChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    check(btnUpperChoice);
                }else
                {
                    uncheck(btnUpperChoice);
                }
            }
        });

        btnMiddleChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    check(btnMiddleChoice);
                }else
                {
                    uncheck(btnMiddleChoice);
                }
            }
        });

        btnLowerChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    check(btnLowerChoice);
                }else
                {
                    uncheck(btnLowerChoice);
                }
            }
        });
    }

    private void check(ToggleButton btn)
    {
        btn.setChecked(true);
        btn.setBackgroundResource(R.drawable.rounded_green_button);
        btn.setTextColor(Color.WHITE);
    }

    private void uncheck(ToggleButton btn)
    {
        btn.setChecked(false);
        btn.setBackgroundResource(R.drawable.rounded_white);
        btn.setTextColor(Color.BLACK);
    }

}