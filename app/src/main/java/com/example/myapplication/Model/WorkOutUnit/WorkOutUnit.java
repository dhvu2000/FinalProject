package com.example.myapplication.Model.WorkOutUnit;

import com.example.myapplication.Model.User.User;

public class WorkOutUnit {
    private int id;
    private String name;
    private User createdBy;

    public WorkOutUnit() {
    }

    public WorkOutUnit(int id, String name, User createdBy) {
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
