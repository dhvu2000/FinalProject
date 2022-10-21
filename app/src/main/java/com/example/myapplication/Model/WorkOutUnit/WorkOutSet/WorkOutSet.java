package com.example.myapplication.Model.WorkOutUnit.WorkOutSet;

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.WorkOutUnit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkOutSet extends WorkOutUnit implements Serializable {

    private int preTime;
    private int restTime;
    private List<SetExercise> exercises;

    public WorkOutSet() {
    }

    public WorkOutSet(int id, String name, Users createdBy, String img,
                      int preTime, int restTime, List<SetExercise> exercises) {
        super(id, name, createdBy, img);
        this.preTime = preTime;
        this.restTime = restTime;
        this.exercises = exercises;
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

    public List<SetExercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<SetExercise> exercises) {
        this.exercises = exercises;
    }
}
