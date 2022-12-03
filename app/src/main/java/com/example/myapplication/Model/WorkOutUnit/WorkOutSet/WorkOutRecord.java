package com.example.myapplication.Model.WorkOutUnit.WorkOutSet;

import com.example.myapplication.Model.User.Users;

import java.io.Serializable;

public class WorkOutRecord implements Serializable {

    private int id;

    private Users user;


    private WorkOutSet workOutSet;

    private String time;

    private long length;

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
