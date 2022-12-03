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

    @Column
    private int progress;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "routine_id", nullable = false)
    private Routine routine;

    public RoutineAct() {
    }

    public RoutineAct(String actTime, int progress, Users user, Routine routine) {
        this.actTime = actTime;
        this.progress = progress;
        this.user = user;
        this.routine = routine;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }
}
