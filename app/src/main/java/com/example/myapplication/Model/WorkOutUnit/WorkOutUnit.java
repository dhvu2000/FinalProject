package com.example.myapplication.Model.WorkOutUnit;

import com.example.myapplication.Model.User.Users;

import java.io.Serializable;

public class WorkOutUnit implements Serializable {
    private int id;
    private String name;
    private Users createdBy;

    public WorkOutUnit() {
    }

    public WorkOutUnit(int id, String name, Users createdBy) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }
}
