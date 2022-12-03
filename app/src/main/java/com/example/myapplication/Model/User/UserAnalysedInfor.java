package com.example.myapplication.Model.User;

import java.io.Serializable;
import java.util.Date;

public class UserAnalysedInfor implements Serializable {

    private int id;

    private String target;

    private String frequency;

    private String focusedArea;

    private String updatedDate;

    private Users user;

    public UserAnalysedInfor() {
    }

    public UserAnalysedInfor(String target, String frequency,
                             String focusedArea, String updatedDate) {
        this.target = target;
        this.frequency = frequency;
        this.focusedArea = focusedArea;
        this.updatedDate = updatedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFocusedArea() {
        return focusedArea;
    }

    public void setFocusedArea(String focusedArea) {
        this.focusedArea = focusedArea;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
