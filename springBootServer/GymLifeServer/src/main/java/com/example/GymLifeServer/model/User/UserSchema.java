package com.example.GymLifeServer.model.User;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class UserSchema implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private double weight;
    @Column
    private double height;
    @Column
    private String updatedDate;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonBackReference
    private Users user;

    public UserSchema() {
    }

    public UserSchema(double weight, double height, String updatedDate) {
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
        return weight/(height * height);
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
