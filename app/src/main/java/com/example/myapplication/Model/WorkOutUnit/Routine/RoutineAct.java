package com.example.myapplication.Model.WorkOutUnit.Routine;

import com.example.myapplication.Model.User.Users;

import java.io.Serializable;
import java.util.Date;

public class RoutineAct implements Serializable {
    private Date actTime;
    private Users user;
    private RoutineDay routineDay;

    public RoutineAct() {
    }

    public RoutineAct(Date actTime, Users user, RoutineDay routineDay) {
        this.actTime = actTime;
        this.user = user;
        this.routineDay = routineDay;
    }

    public Date getActTime() {
        return actTime;
    }

    public void setActTime(Date actTime) {
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