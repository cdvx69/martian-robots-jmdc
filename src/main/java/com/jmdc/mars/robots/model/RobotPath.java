package com.jmdc.mars.robots.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="ROBOTPATH")
public class RobotPath implements Serializable {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="INITIALPOS")
    private String initialPos;

    @Column(name="FINALPOS")
    private String finalPos;

    @Column(name="MOVEMENT")
    private String movement;

    @Column(name="LOST")
    private boolean lost;

    @Column(name="DATE")
    private Date date;

    public RobotPath() {
    }

    public RobotPath(String initialPos, String finalPos, String movement, boolean lost, Date date) {
        this.initialPos = initialPos;
        this.finalPos = finalPos;
        this.lost = lost;
        this.date = date;
        this.movement = movement;
    }

    public RobotPath(String initialPos, String finalPos) {
        this.initialPos = initialPos;
        this.finalPos = finalPos;
    }

    public String getInitialPos() {
        return initialPos;
    }

    public void setInitialPos(String initialPos) {
        this.initialPos = initialPos;
    }

    public String getFinalPos() {
        return finalPos;
    }

    public void setFinalPos(String finalPos) {
        this.finalPos = finalPos;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }
}
