package com.example.myapplication.Model.WorkOutUnit.Routine;

import com.example.myapplication.Model.User.User;
import com.example.myapplication.Model.WorkOutUnit.WorkOutUnit;

import java.util.ArrayList;

public class Routine extends WorkOutUnit {
    private int dayNum;
    private int level;
    private ArrayList<RoutineDay> days;

    public Routine() {
    }

    public Routine(int id, String name, User createdBy,
                   int dayNum, int level, ArrayList<RoutineDay> days) {
        super(id, name, createdBy);
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

    public ArrayList<RoutineDay> getDays() {
        return days;
    }

    public void setDays(ArrayList<RoutineDay> days) {
        this.days = days;
    }
}
