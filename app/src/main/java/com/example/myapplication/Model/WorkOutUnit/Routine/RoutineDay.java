package com.example.myapplication.Model.WorkOutUnit.Routine;

import com.example.myapplication.Model.User.User;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutSet;

import java.util.ArrayList;

public class RoutineDay extends WorkOutSet {

    private int sequence;

    public RoutineDay() {
    }

    public RoutineDay(int sequence) {
        this.sequence = sequence;
    }

    public RoutineDay(int id, String name, User createdBy, int preTime, int restTime, ArrayList<SetExercise> exercises, int sequence) {
        super(id, name, createdBy, preTime, restTime, exercises);
        this.sequence = sequence;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
