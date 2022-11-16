package com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet;

import com.example.GymLifeServer.model.WorkOutUnit.Exercise;
import com.example.GymLifeServer.model.WorkOutUnit.WorkOutUnit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SetExercise implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int timeLength;
    @Column
    private int repNum;
    @Column
    private int sequence;


    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "set_id", nullable = false)
    @JsonBackReference
    private WorkOutSet workOutSet;

    public SetExercise() {
    }

    public SetExercise(int timeLength, int repNum, int sequence, Exercise exercise) {
        this.timeLength = timeLength;
        this.repNum = repNum;
        this.sequence = sequence;
        this.exercise = exercise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WorkOutSet getWorkOutSet() {
        return workOutSet;
    }

    public void setWorkOutSet(WorkOutSet workOutSet) {
        this.workOutSet = workOutSet;
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
        return sequence;
    }

    public void setSequence(int sequence) {
        sequence = sequence;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
