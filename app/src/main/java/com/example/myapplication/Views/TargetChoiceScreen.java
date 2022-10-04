package com.example.myapplication.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.myapplication.R;

public class TargetChoiceScreen extends AppCompatActivity {

    ToggleButton btnBuildMuscleChoice;
    ToggleButton btnLoseWeightChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_target_choice_screen);

        btnBuildMuscleChoice = findViewById(R.id.btnMuscleChoice);
        btnLoseWeightChoice = findViewById(R.id.btnLoseWeightChoice);

        btnBuildMuscleChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    check(btnBuildMuscleChoice);
                }else
                {
                    uncheck(btnBuildMuscleChoice);
                }
            }
        });

        btnLoseWeightChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    check(btnLoseWeightChoice);
                }else
                {
                    uncheck(btnLoseWeightChoice);
                }
            }
        });
    }



    private void check(ToggleButton btn)
    {
        uncheckAll();
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

    private void uncheckAll()
    {
        uncheck(btnBuildMuscleChoice);
        uncheck(btnLoseWeightChoice);
    }
}