package com.example.myapplication.Model.WorkOutUnit;

import com.example.myapplication.Model.User.User;

public class Exercise extends WorkOutUnit{

    private String introduction;
    private String guideline;
    private String img;
    private String type;

    public Exercise() {
    }

    public Exercise(int id, String name, User createdBy,
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
