package com.jmdc.mars.robots.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="ROBOTDANGERPOINTS")
public class RobotDangerPoint implements Serializable {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="COORDX")
    private String coordx;

    @Column(name="COORDY")
    private String coordy;

    @Column(name="DATE")
    private Date date;

    public RobotDangerPoint() {
    }

    public RobotDangerPoint(String coordx, String coordy, Date date) {
        this.coordx = coordx;
        this.coordy = coordy;
        this.date = date;
    }

    public String getCoordx() {
        return coordx;
    }

    public void setCoordx(String coordx) {
        this.coordx = coordx;
    }

    public String getCoordy() {
        return coordy;
    }

    public void setCoordy(String coordy) {
        this.coordy = coordy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public String toString() {
        return coordx.concat(coordy);
    }
}
