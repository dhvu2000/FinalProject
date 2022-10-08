package com.example.myapplication.Model.WorkOutUnit.WorkOutSet;

import com.example.myapplication.Model.WorkOutUnit.Exercise;

import java.io.Serializable;

public class SetExercise implements Serializable {

    private int id;
    private int timeLength;
    private int repNum;
    private int Sequence;
    private Exercise exercise;

    public SetExercise() {
    }

    public SetExercise(int timeLength, int repNum, int sequence, Exercise exercise) {
        this.timeLength = timeLength;
        this.repNum = repNum;
        Sequence = sequence;
        this.exercise = exercise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(int timeLength) {
        this.timeLength = timeLength;
    }

    public int getRepNum() {
        return repNum;
    }

    public void setRepNum(int repNum) {
        this.repNum = repNum;
    }

    public int getSequence() {
        return Sequence;
    }

    public void setSequence(int sequence) {
        Sequence = sequence;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
