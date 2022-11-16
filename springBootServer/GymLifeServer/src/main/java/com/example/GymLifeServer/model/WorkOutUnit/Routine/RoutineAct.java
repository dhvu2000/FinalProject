package com.example.GymLifeServer.model.WorkOutUnit.Routine;

import com.example.GymLifeServer.model.User.Users;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
public class RoutineAct implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

    @Column
    private String actTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "routine_day_id", nullable = false)
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
