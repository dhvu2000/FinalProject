package com.example.myapplication.Model.WorkOutUnit.WorkOutSet;

import com.example.myapplication.Model.User.User;
import com.example.myapplication.Model.WorkOutUnit.WorkOutUnit;

import java.util.ArrayList;

public class WorkOutSet extends WorkOutUnit {

    private int exerciseNum;
    private int preTime;
    private int restTime;
    private ArrayList<SetExercise> exercises;

    public WorkOutSet() {
    }

    public WorkOutSet(int id, String name, User createdBy,
                      int preTime, int restTime, ArrayList<SetExercise> exercises) {
        super(id, name, createdBy);
        this.exerciseNum = exercises.size();
        this.preTime = preTime;
        this.restTime = restTime;
        this.exercises = exercises;
    }

    public int getExerciseNum() {
        return exerciseNum;
    }

    private void setExerciseNum(int exerciseNum) {
        this.exerciseNum = exercises.size();
    }

    public int getPreTime() {
        return preTime;
    }

    public void setPreTime(int preTime) {
        this.preTime = preTime;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public ArrayList<SetExercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<SetExercise> exercises) {
        this.exercises = exercises;
        this.exerciseNum = exercises.size();
    }
}
