package com.example.myapplication.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class WelcomeScreen extends AppCompatActivity {

    Animation topAnimation;
    Animation bottomAnimation;
    ImageView image;
    TextView txtNameApp;
    TextView txtWelcome;
    Button btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setExitTransition(new Slide());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_screen);

        //animation
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //initialize
        image = findViewById(R.id.image);
        txtNameApp = findViewById(R.id.txtNameApp);
        txtWelcome = findViewById(R.id.txtWelcome);
        btnContinue = findViewById(R.id.btnContinue);

        //set Animation
        image.setAnimation(topAnimation);
        txtNameApp.setAnimation(topAnimation);
        txtWelcome.setAnimation(bottomAnimation);
        btnContinue.setAnimation(bottomAnimation);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeScreen.this, AccountConfirmScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                WelcomeScreen.this.finish();
            }
        });


    }
}