package com.example.myapplication.Model.WorkOutUnit.Routine;

import com.example.myapplication.Model.User.User;

import java.util.Date;

public class RoutineAct {
    private Date actTime;
    private User user;
    private RoutineDay routineDay;

    public RoutineAct() {
    }

    public RoutineAct(Date actTime, User user, RoutineDay routineDay) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RoutineDay getRoutineDay() {
        return routineDay;
    }

    public void setRoutineDay(RoutineDay routineDay) {
        this.routineDay = routineDay;
    }
}
