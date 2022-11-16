package com.example.myapplication.Adapter.FindAndAddExerciseItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.WorkOutUnit.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.Views.CollectionsScreen.BothUseScreen.SettingExerciseDialog;
import com.example.myapplication.Views.ExerciseScreen.DetailExerciseDialog;

import java.util.List;

public class FindAndAddExerciseAdapter extends RecyclerView.Adapter<FindAndAddExerciseHolder> {


    private List<Exercise> exercises;
    private Context context;

    public FindAndAddExerciseAdapter(List<Exercise> exercises, Context context)
    {
        this.exercises = exercises;
        this.context = context;
    }


    @NonNull
    @Override
    public FindAndAddExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_find_exercise_item,parent,false);

        return new FindAndAddExerciseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindAndAddExerciseHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.txtName.setText(exercise.getName());
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailExerciseDialog detailExerciseDialog = new DetailExerciseDialog(exercise);
                detailExerciseDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"AddExerciseDialog");
            }
        });

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingExerciseDialog settingExerciseDialog = new SettingExerciseDialog(exercise);
                settingExerciseDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"SettingExerciseDialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
}
