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

public class WorkoutProcessScreen2 extends AppCompatActivity {


    ImageButton btnSkip, btnPauseContinue, btnStop;
    TextView txtName, txtBtnPauseContinue, txtReps, txtTime;
    ImageView img;
    int sequence;
    WorkOutSet workOutSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_workout_process_screen2);

        txtName = findViewById(R.id.txtName);
        btnSkip = findViewById(R.id.btnSkip);
        btnPauseContinue = findViewById(R.id.btnPauseContinue);
        btnStop = findViewById(R.id.btnStop);
        txtBtnPauseContinue = findViewById(R.id.txtBtnPauseContinue);
        txtReps = findViewById(R.id.txtRepsNum);
        txtTime = findViewById(R.id.txtTime);
        img = findViewById(R.id.img);

        setValue();

        SetExercise setExercise = workOutSet.getExercises().get(sequence);

        txtName.setText(setExercise.getExercise().getName());

        if(setExercise.getExercise().getImg() != null && !setExercise.getExercise().getImg().trim().equals(""))
        {
            Picasso.get().load(setExercise.getExercise().getImg()).error(R.drawable.add_image).into(img);
        }else{
            Picasso.get().load(R.drawable.add_image).into(img);
        }

        txtReps.setText("x"+setExercise.getRepNum());
        txtTime.setText(setExercise.getTimeLength()+" secs");

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
                sequence ++;
                if(sequence == workOutSet.getExercises().size())
                {
                    moveToEnd();
                }
                else{
                    moveToNext();
                }
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

    private void moveToEnd() {
        Intent intent = new Intent(WorkoutProcessScreen2.this, WorkOutProcessEnd.class);
        startActivity(intent);
        finish();
    }

    private void moveToNext()
    {
        Intent intent = new Intent(WorkoutProcessScreen2.this, WorkOutProcessScreen1.class);
        intent.putExtra("set", workOutSet);
        intent.putExtra("sequence", sequence);
        startActivity(intent);
        finish();
    }
}