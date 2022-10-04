package com.example.myapplication.Model.User;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private int id;
    private String username;
    private Date dob;
    private String password;
    private String email;
    private String gender;
    private String type;
    private ArrayList<UserAnalysedInfor> infor;

    public User() {
    }

    public User(int id, String username, Date dob,
                String password, String email, String gender,
                String type, ArrayList<UserAnalysedInfor> infor) {
        this.id = id;
        this.username = username;
        this.dob = dob;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.type = type;
        this.infor = infor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<UserAnalysedInfor> getInfor() {
        return infor;
    }

    public void setInfor(ArrayList<UserAnalysedInfor> infor) {
        this.infor = infor;
    }
}
