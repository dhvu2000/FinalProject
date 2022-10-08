package com.example.GymLifeServer.model.WorkOutUnit.WorkOutSet;

import com.example.GymLifeServer.model.WorkOutUnit.Exercise;
import com.example.GymLifeServer.model.WorkOutUnit.WorkOutUnit;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SetExercise implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int timeLength;
    @Column
    private int repNum;
    @Column
    private int Sequence;


    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "set_id", nullable = false)
    private WorkOutSet workOutSet;

    public SetExercise() {
    }

    public SetExercise(int timeLength, int repNum, int sequence, Exercise exercise) {
        this.timeLength = timeLength;
        this.repNum = repNum;
        Sequence = sequence;
        this.exercise = exercise;
    }

    public int getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(int timeLength) {
        this.timeLength = timeLength;
    }

    public int getRepNum() {
        return repNum;
    }

    public void setRepNum(int repNum) {
        this.repNum = repNum;
    }

    public int getSequence() {
        return Sequence;
    }

    public void setSequence(int sequence) {
        Sequence = sequence;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
