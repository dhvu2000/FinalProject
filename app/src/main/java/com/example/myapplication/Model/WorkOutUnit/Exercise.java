package com.example.myapplication.Model.WorkOutUnit;

import androidx.annotation.NonNull;

import com.example.myapplication.Model.User.Users;

import java.io.Serializable;

public class Exercise extends WorkOutUnit implements Serializable {

    private String introduction;
    private String guideline;
    private String type;
    private double calories;

    public Exercise() {
    }

    public Exercise(int id, String name, Users createdBy, String img,
                    String introduction, String guideline, String type) {
        super(id, name, createdBy, img);
        this.introduction = introduction;
        this.guideline = guideline;
        this.type = type;
    }

    public Exercise(String name, Users createdBy, String img,
                    String introduction, String guideline, String type) {
        super(name, createdBy, img);
        this.introduction = introduction;
        this.guideline = guideline;
        this.type = type;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getGuideline() {
        return guideline;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='"+super.getName()+'\''+
                ", introduction='" + introduction + '\'' +
                ", guideline='" + guideline + '\'' +
                ", type='" + type + '\'' +
                ", createdBy='" + super.getCreatedBy().getUsername() + '\'' +
                '}';
    }
}
