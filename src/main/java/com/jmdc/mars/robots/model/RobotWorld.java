package com.jmdc.mars.robots.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RobotWorld {

    @NotNull
    @Max(value = 50, message = "maximum value for x axis in the map is 50")
    @Min(value = 0, message = "minimum value for x axis in the map is 0")
    private int xAxis;

    @NotNull
    @Max(value = 50, message = "maximum value for y axis in the map is 50")
    @Min(value = 0, message = "minimum value for y axis in the map is 0")
    private int yAxis;

    public RobotWorld(@JsonProperty("x")int x, @JsonProperty("y")int y) {
        this.xAxis = x;
        this.yAxis = y;
    }

    public boolean isOutOfWorld(int x, int y) {
        return x < 0 || x > this.xAxis || y < 0 || y > this.yAxis;
    }

    public int getxAxis() {
        return xAxis;
    }

    public int getyAxis() {
        return yAxis;
    }
}
