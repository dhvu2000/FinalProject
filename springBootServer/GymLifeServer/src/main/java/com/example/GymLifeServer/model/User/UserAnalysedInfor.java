package com.example.GymLifeServer.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class UserAnalysedInfor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private double weight = 0;
    @Column
    private double height = 0;
    @Column
    private String target;
    @Column
    private String frequency;
    @Column
    private String focusedArea;
    @Column
    private Date updatedDate;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private Users user;

    public UserAnalysedInfor() {
    }

    public UserAnalysedInfor(double weight, double height,
                             String target, String frequency,
                             String focusedArea, Date updatedDate) {
        this.weight = weight;
        this.height = height;
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

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
