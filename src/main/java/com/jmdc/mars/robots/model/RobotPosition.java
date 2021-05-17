package com.jmdc.mars.robots.model;

import lombok.*;

import java.util.Arrays;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RobotPosition {

    @Getter @Setter private int robotx;
    @Getter @Setter private int roboty;
    @Getter @Setter private Orientation orientation;

    public enum Orientation {
        NORTH("N",1), SOUTH("S",-1), WEST("W", -1), EAST("E",1);
        private String initial;
        private int movement;
        private Orientation(String initial, int movement) {
            this.initial = initial;
            this.movement = movement;
        }
        public String initial() {
            return this.initial;
        }

        public int movement() {
            return this.movement;
        }

        public static Orientation orientationByName(String name) {
            return Arrays.stream(Orientation.values()).filter(x -> x.initial.equals(name)).findFirst().get();
        }
    }

    public String getStringCoordinates() {
        return String.valueOf(robotx).concat(String.valueOf(roboty));
    }
}
