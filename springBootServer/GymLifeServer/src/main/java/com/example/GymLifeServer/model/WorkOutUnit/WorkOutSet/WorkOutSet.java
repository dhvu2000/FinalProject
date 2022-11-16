package com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet;

import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.WorkOutUnit.WorkOutUnit;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "workOutSet")
    @JsonManagedReference
    private List<SetExercise> exercises;

    @Column
    private String type;

    public WorkOutSet() {
    }

    public WorkOutSet( String name, Users createdBy, String img,
                      int preTime, int restTime, List<SetExercise> exercises,
                        String type) {
        super(name, createdBy, img);
        this.preTime = preTime;
        this.restTime = restTime;
        this.exercises = exercises;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
