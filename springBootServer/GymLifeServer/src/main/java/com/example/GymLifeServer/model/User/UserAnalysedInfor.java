package com.example.GymLifeServer.model.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tblUserAnalysedInfor")
public class UserAnalysedInfor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double weight = 0;
    private double height = 0;
    private String target;
    private String frequency;
    private String focusedArea;
    private Date updatedDate;

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
