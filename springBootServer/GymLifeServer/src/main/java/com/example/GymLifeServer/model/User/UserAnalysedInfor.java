package com.example.GymLifeServer.model.User;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class UserAnalysedInfor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String target;
    @Column
    private String frequency;
    @Column
    private String focusedArea;
    @Column
    private String updatedDate;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonBackReference
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
