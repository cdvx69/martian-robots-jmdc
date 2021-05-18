package com.jmdc.mars.robots.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public class RobotResponse {

    private List<Robot> robots;
    private String message;

    public RobotResponse() {}


    public List<Robot> getRobots() {
        if(robots == null) {
            robots = new ArrayList<>();
        }
        return robots;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RobotResponse{" +
                "robots=" + robots +
                ", message='" + message + '\'' +
                '}';
    }
}
