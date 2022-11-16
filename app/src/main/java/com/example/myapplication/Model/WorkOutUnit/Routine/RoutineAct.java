package com.example.myapplication.Model.WorkOutUnit.Routine;

import com.example.myapplication.Model.User.Users;

import java.io.Serializable;
import java.util.Date;

public class RoutineAct implements Serializable {

    private int id;
    private String actTime;
    private Users user;
    private RoutineDay routineDay;

    public RoutineAct() {
    }

    public RoutineAct(String actTime, Users user, RoutineDay routineDay) {
        this.actTime = actTime;
        this.user = user;
        this.routineDay = routineDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActTime() {
        return actTime;
    }

    public void setActTime(String actTime) {
        this.actTime = actTime;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public RoutineDay getRoutineDay() {
        return routineDay;
    }

    public void setRoutineDay(RoutineDay routineDay) {
        this.routineDay = routineDay;
    }
}
