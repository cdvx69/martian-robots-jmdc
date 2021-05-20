package com.jmdc.mars.robots.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Robot {
    private String position;
    private String movement;
    private boolean lost;
    private String message;

    public Robot(@JsonProperty("position") String position, @JsonProperty("movement") String movement, @JsonProperty("lost") boolean lost) {
        this.position = position;
        this.movement = movement;
        this.lost = lost;
    }

    public Robot(String position, String movement) {
        this.position = position;
        this.movement = movement;
    }

    public String getPosition() {
        return position;
    }

    //@JsonIgnore
    public String getMovement() {
        return movement;
    }

    public boolean isLost() {
        return lost;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "position='" + position + '\'' +
                ", movement='" + movement + '\'' +
                ", lost=" + lost +
                ", message='" + message + '\'' +
                '}';
    }
}
