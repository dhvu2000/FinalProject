package com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet;

import com.example.GymLifeServer.model.User.Users;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class WorkOutRecord implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "set_id", nullable = false)
    private WorkOutSet workOutSet;


    @Column
    private String time;

    @Column
    private long length;

    @Column
    private double totalCalories;

    public WorkOutRecord() {
    }

    public WorkOutRecord(Users user, WorkOutSet workOutSet, String time, long length, double totalCalories) {
        this.user = user;
        this.workOutSet = workOutSet;
        this.time = time;
        this.length = length;
        this.totalCalories = totalCalories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public WorkOutSet getWorkOutSet() {
        return workOutSet;
    }

    public void setWorkOutSet(WorkOutSet workOutSet) {
        this.workOutSet = workOutSet;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }
}
