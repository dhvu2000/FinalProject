package com.example.myapplication.Model.User;

import org.intellij.lang.annotations.PrintFormat;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


public class Users implements Serializable {

    private int id;

    private String username;

    private String dob;

    private String password;

    private String email;

    private String gender;

    private String type;

    private List<UserAnalysedInfor> infor;

    public Users() {
    }

    public Users(int id, String username, String dob,
                 String password, String email, String gender,
                 String type, List<UserAnalysedInfor> infor) {
        this.id = id;
        this.username = username;
        this.dob = dob;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.type = type;
        this.infor = infor;
    }

    public Users(String username, String dob,
                 String password, String email, String gender,
                 String type, List<UserAnalysedInfor> infor) {
        this.username = username;
        this.dob = dob;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.type = type;
        this.infor = infor;
    }

    public List<UserAnalysedInfor> getInfor() {
        return infor;
    }

    public void setInfor(List<UserAnalysedInfor> infor) {
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", dob=" + dob +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

