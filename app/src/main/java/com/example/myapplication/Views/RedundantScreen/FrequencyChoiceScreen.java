package com.example.myapplication.Views.RedundantScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.myapplication.R;

public class FrequencyChoiceScreen extends AppCompatActivity {

    ToggleButton btnLittle;
    ToggleButton btnNormal;
    ToggleButton btnIntense;
    ToggleButton btnVeryIntense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_frequency_choice_screen);

        btnLittle = findViewById(R.id.btnLittle);
        btnNormal = findViewById(R.id.btnNormal);
        btnIntense = findViewById(R.id.btnIntense);
        btnVeryIntense = findViewById(R.id.btnVeryIntense);

        btnLittle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    check(btnLittle);
                }else
                {
                    uncheck(btnLittle);
                }
            }
        });

        btnNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    check(btnNormal);
                }else
                {
                    uncheck(btnNormal);
                }
            }
        });

        btnIntense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    check(btnIntense);
                }else
                {
                    uncheck(btnIntense);
                }
            }
        });

        btnVeryIntense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    check(btnVeryIntense);
                }else
                {
                    uncheck(btnVeryIntense);
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
        uncheck(btnIntense);
        uncheck(btnNormal);
        uncheck(btnLittle);
        uncheck(btnVeryIntense);
    }
}