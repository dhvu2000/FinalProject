package com.example.myapplication.Model.WorkOutUnit.Routine;

import androidx.annotation.NonNull;

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutSet;

import java.io.Serializable;
import java.util.ArrayList;

public class RoutineDay extends WorkOutSet implements Serializable{

    private int sequence;
    private Routine routine;

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public RoutineDay() {
    }

    public RoutineDay(int sequence) {
        this.sequence = sequence;
    }


    public RoutineDay(String name, Users createdBy, String img,
                      int preTime, int restTime, ArrayList<SetExercise> exercises,String type, int sequence) {
        super(name, createdBy, img,  preTime, restTime, exercises, type);
        this.sequence = sequence;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
