package com.example.myapplication.Views.CollectionsScreen.BothUseScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.SetExerciseItem.SetExerciseAdapter;
import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineDay;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutSet;
import com.example.myapplication.R;
import com.example.myapplication.Views.CollectionsScreen.WorkOutProcessScreen.WorkOutProcessScreen1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DetailCollectionScreen extends AppCompatActivity {

    private Button btnBack;
    private ImageButton btnChange, btnStart;
    private TextView txtName, txtExerciseNum, txtPre, txtRest;
    private RecyclerView recyclerView;
    private WorkOutSet workOutSet;
    private String type;
    private ArrayList<SetExercise> setExercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail_collection_screen);

        btnBack = findViewById(R.id.btnBack);
        btnStart = findViewById(R.id.btnStart);
        btnChange = findViewById(R.id.btnChange);
        txtName = findViewById(R.id.txtName);
        txtExerciseNum = findViewById(R.id.txtExerciseNum);
        txtPre = findViewById(R.id.txtPreTime);
        txtRest = findViewById(R.id.txtRestTime);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setWorkOutSet();
        populateListView();

        setButtons();

    }

    private void setButtons() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoutineDay routineDay = (RoutineDay) workOutSet;
                Intent intent = new Intent(DetailCollectionScreen.this, UpdateSaveCollectionScreen.class);
                intent.putExtra("type",type);
                //remember to put Routine inside Routine Day
                intent.putExtra("set",routineDay);
                startActivity(intent);
                finish();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(workOutSet == null || workOutSet.getExercises().size() == 0)
                {
                    Toast.makeText(DetailCollectionScreen.this, "No exercise yet", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(DetailCollectionScreen.this, WorkOutProcessScreen1.class);
                intent.putExtra("set", workOutSet);
                intent.putExtra("sequence", 0);
                startActivity(intent);
            }
        });
    }

    public void setWorkOutSet(){
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if(type != null && type.equals("routine"))
        {
            workOutSet = (RoutineDay) intent.getSerializableExtra("set");
        }
        else
        {
            workOutSet = (WorkOutSet) intent.getSerializableExtra("set");
        }

        if(workOutSet.getName()!= null && !workOutSet.getName().trim().equals("")) txtName.setText(workOutSet.getName());
        txtPre.setText(workOutSet.getPreTime()+"");
        txtRest.setText(workOutSet.getRestTime()+"");

        if(workOutSet.getExercises()!= null && workOutSet.getExercises().size() != 0)
        {
            txtExerciseNum.setText("Number of exercise: "+ workOutSet.getExercises().size());
            setExercises.addAll(workOutSet.getExercises());
        }
        else
        {
            workOutSet.setExercises(new ArrayList<>());
        }
    }

    private void populateListView()
    {
        sortExercises();
        SetExerciseAdapter adapter = new SetExerciseAdapter(setExercises, this);
        recyclerView.setAdapter(adapter);
    }

    private void sortExercises()
    {
        Collections.sort(setExercises, new Comparator<SetExercise>() {
            @Override
            public int compare(SetExercise setExercise, SetExercise t1) {
                Integer a = t1.getSequence();
                Integer b = setExercise.getSequence();
                return b.compareTo(a);
            }
        });
    }
}