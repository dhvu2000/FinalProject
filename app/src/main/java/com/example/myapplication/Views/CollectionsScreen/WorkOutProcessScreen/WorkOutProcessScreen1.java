package com.example.myapplication.Views.CollectionsScreen.WorkOutProcessScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutSet;
import com.example.myapplication.R;
import com.example.myapplication.Views.ExerciseScreen.DetailExerciseDialog;
import com.squareup.picasso.Picasso;

public class WorkOutProcessScreen1 extends AppCompatActivity {

    ProgressBar progressBar;
    ImageButton btnSkip, btnPauseContinue, btnStop;
    TextView txtHead, txtBtnPauseContinue, txtNextExerciseName, txtReps, txtTime;
    ImageView img;
    WorkOutSet workOutSet;
    int sequence;

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

        setValue();

        if(sequence == 0)
        {
            txtHead.setText("Preparation");
        }
        else {
            txtHead.setText("Rest");
        }

        SetExercise setExercise = workOutSet.getExercises().get(sequence);

        txtNextExerciseName.setText("Next: "+ setExercise.getExercise().getName());
        txtReps.setText("x"+setExercise.getRepNum());
        txtTime.setText(setExercise.getTimeLength()+" secs");

        if(setExercise.getExercise().getImg() != null && !setExercise.getExercise().getImg().trim().equals(""))
        {
            Picasso.get().load(setExercise.getExercise().getImg()).error(R.drawable.add_image).into(img);
        }else{
            Picasso.get().load(R.drawable.add_image).into(img);
        }

        setButtons();


    }


    private void setValue() {

        sequence = getIntent().getIntExtra("sequence", 0);
        workOutSet = (WorkOutSet) getIntent().getSerializableExtra("set");
    }


    private void setButtons() {
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNext();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailExerciseDialog dialog = new DetailExerciseDialog(workOutSet.getExercises().get(sequence).getExercise());
                dialog.show(getSupportFragmentManager(), "DetailExerciseDialog");
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void moveToNext()
    {
        Intent  intent = new Intent(WorkOutProcessScreen1.this, WorkoutProcessScreen2.class);
        intent.putExtra("set", workOutSet);
        intent.putExtra("sequence", sequence);
        startActivity(intent);
        finish();
    }


}