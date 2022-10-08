package com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet;

import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.WorkOutUnit.WorkOutUnit;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class WorkOutSet extends WorkOutUnit implements Serializable {

    @Column
    private int preTime;
    @Column
    private int restTime;
    @OneToMany(mappedBy = "workOutSet")
    private List<SetExercise> exercises;

    public WorkOutSet() {
    }

    public WorkOutSet(int id, String name, Users createdBy,
                      int preTime, int restTime, ArrayList<SetExercise> exercises) {
        super(id, name, createdBy);
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
