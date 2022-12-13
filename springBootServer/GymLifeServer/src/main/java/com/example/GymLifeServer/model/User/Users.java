package com.example.GymLifeServer.model.User;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique=true)
    private String username;

    @Column
    private String dob;


    @Column
    private String password;


    @Column(unique=true)
    private String email;


    @Column
    private String gender;

    @Column
    private String type;

    @Column
    private String img;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.REMOVE})
    @JsonManagedReference
    private UserAnalysedInfor infor;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    @JsonManagedReference
    private List<UserSchema> schemas;

    public Users() {
    }

    public Users(int id, String username, String dob,
                 String password, String email, String gender,
                 String type, UserAnalysedInfor infor) {
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
                 String type, UserAnalysedInfor infor, List<UserSchema> schemas) {
        this.username = username;
        this.dob = dob;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.type = type;
        this.infor = infor;
        this.schemas = schemas;
    }

    public List<UserSchema> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<UserSchema> schemas) {
        this.schemas = schemas;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public UserAnalysedInfor getInfor() {
        return infor;
    }

    public void setInfor(UserAnalysedInfor infor) {
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
