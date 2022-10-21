package com.example.myapplication.Model.WorkOutUnit;

import com.example.myapplication.Model.User.Users;

import java.io.Serializable;

public class WorkOutUnit implements Serializable {
    private int id;
    private String name;
    private Users createdBy;
    private String img;

    public WorkOutUnit() {
    }


    public WorkOutUnit(int id, String name, Users createdBy, String img) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.img = img;
    }

    public WorkOutUnit(String name, Users createdBy, String img) {
        this.name = name;
        this.createdBy = createdBy;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
