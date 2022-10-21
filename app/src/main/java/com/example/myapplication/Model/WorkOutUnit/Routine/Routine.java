package com.example.myapplication.Model.WorkOutUnit.Routine;

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.WorkOutUnit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Routine extends WorkOutUnit implements Serializable {
    private int dayNum;
    private int level;
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
