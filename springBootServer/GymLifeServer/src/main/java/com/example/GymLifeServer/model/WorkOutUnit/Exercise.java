package com.example.GymLifeServer.model.WorkOutUnit;

import com.example.GymLifeServer.model.User.Users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
public class Exercise extends WorkOutUnit implements Serializable {

    @Column
    private String introduction;
    @Column
    private String guideline;
    @Column
    private String img;
    @Column
    private String type;

    public Exercise() {
    }

    public Exercise(int id, String name, Users createdBy,
                    String introduction, String guideline, String img, String type) {
        super(id, name, createdBy);
        this.introduction = introduction;
        this.guideline = guideline;
        this.img = img;
        this.type = type;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getGuideline() {
        return guideline;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
