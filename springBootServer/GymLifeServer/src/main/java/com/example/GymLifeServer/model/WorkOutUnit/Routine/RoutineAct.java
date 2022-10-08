package com.example.GymLifeServer.model.WorkOutUnit.Routine;

import com.example.GymLifeServer.model.User.Users;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
public class RoutineAct implements Serializable {
    @Column
    private Date actTime;
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    @Id
    @ManyToOne
    @JoinColumn(name = "routine_day_id", nullable = false)
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
