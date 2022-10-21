package com.example.GymLifeServer.model.WorkOutUnit.Routine;

import com.example.GymLifeServer.model.User.Users;
import com.example.GymLifeServer.model.WorkOutUnit.WorkOutUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Routine extends WorkOutUnit implements Serializable {
    @Column
    private int dayNum;
    @Column
    private int level;

    @OneToMany(mappedBy = "routine")
    private List<RoutineDay> days;

    public Routine() {
    }

    public Routine(int id, String name, Users createdBy, String img,
                   int dayNum, int level, ArrayList<RoutineDay> days) {
        super(id, name, createdBy,img);
        this.dayNum = dayNum;
        this.level = level;
        this.days = days;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<RoutineDay> getDays() {
        return days;
    }

    public void setDays(ArrayList<RoutineDay> days) {
        this.days = days;
    }
}
