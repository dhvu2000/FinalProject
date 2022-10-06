package com.example.GymLifeServer.model.User;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tblUser")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String username;

    @NotNull
    private Date dob;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private String gender;

    @NotNull
    private String type;

    @OneToMany(mappedBy = "tblUser")
    private List<UserAnalysedInfor> infor;

    public Users() {
    }

    public Users(int id, String username, Date dob,
                 String password, String email, String gender,
                 String type) {
        this.id = id;
        this.username = username;
        this.dob = dob;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.type = type;
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
