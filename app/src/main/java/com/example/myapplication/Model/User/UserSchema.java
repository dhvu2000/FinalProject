package com.example.myapplication.Model.User;

import java.io.Serializable;
import java.util.Date;

public class UserSchema implements Serializable {

    private int id;

    private double weight;

    private double height;

    private String updatedDate;

    private Users user;

    public UserSchema() {
    }

    public UserSchema(double height, double weight, String updatedDate) {
        this.weight = weight;
        this.height = height;
        this.updatedDate = updatedDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return weight/(height * height/10000.0);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }
}
