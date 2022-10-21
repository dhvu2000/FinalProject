package com.example.GymLifeServer.model.WorkOutUnit;

import ch.qos.logback.classic.db.names.ColumnName;
import com.example.GymLifeServer.model.User.Users;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class WorkOutUnit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "createdBy", nullable = false)
    private Users createdBy;

    @Column
    private String img;

    public WorkOutUnit() {
    }

    public WorkOutUnit(int id, String name, Users createdBy, String img) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.img = img;
    }

    public WorkOutUnit(String name, Users createdBy, String img) {
        this.name = name;
        this.createdBy = createdBy;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }
}
