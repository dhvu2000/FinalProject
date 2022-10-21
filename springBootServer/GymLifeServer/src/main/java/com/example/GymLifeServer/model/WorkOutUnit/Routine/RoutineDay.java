package com.example.GymLifeServer.model.WorkOutUnit.Routine;

import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet.WorkOutSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class RoutineDay extends WorkOutSet implements Serializable {


    @Column
    private int sequence;
    @ManyToOne
    @JoinColumn(name = "routine_id", nullable = false)
    private Routine routine;



    public RoutineDay() {
    }

    public RoutineDay(int sequence) {
        this.sequence = sequence;
    }

    public RoutineDay(int id, String name, Users createdBy, String img,
                      int preTime, int restTime, ArrayList<SetExercise> exercises, int sequence) {
        super(id, name, createdBy, img,  preTime, restTime, exercises);
        this.sequence = sequence;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }
}
