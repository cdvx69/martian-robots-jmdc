package com.jmdc.mars.robots.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public class RobotRequest {
    @Valid
    @NotNull(message = "robotWorld must not be null")
    private RobotWorld robotWorld;

    @NotEmpty(message = "robot list is empty !!!")
    private List<Robot> robots;

    public RobotRequest(@JsonProperty("world") RobotWorld robotWorld, @JsonProperty("robots") List<Robot> robots) {
        this.robotWorld = robotWorld;
        this.robots = robots;
    }

    public RobotWorld getWorld() {
        return this.robotWorld;
    }

    public List<Robot> getRobots() {
        return this.robots;
    }

    @Override
    public String toString() {
        return "RobotRequest{" +
                "robotWorld=" + robotWorld +
                ", robots=" + robots.toString() +
                '}';
    }
}
