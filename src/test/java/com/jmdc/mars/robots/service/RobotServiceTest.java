package com.jmdc.mars.robots.service;

import com.jmdc.mars.robots.model.Robot;
import com.jmdc.mars.robots.model.RobotRequest;
import com.jmdc.mars.robots.model.RobotResponse;
import com.jmdc.mars.robots.model.RobotWorld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RobotServiceTest {

    private RobotService robotService;

    @Mock
    private RobotRequest mockRobotRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        robotService = new RobotService();
    }

    @Test
    public void exampleInput() {
        Robot r1 = new Robot("11E", "RFRFRFRF");
        Robot r2 = new Robot("32N", "FRRFLLFFRRFLL");
        Robot r3 = new Robot("03W", "LLFFFLFLFL");

        when(mockRobotRequest.getRobots()).thenReturn(Arrays.asList(r1,r2,r3));
        when(mockRobotRequest.getWorld()).thenReturn(new RobotWorld(5,3));

        RobotResponse robotResponse = robotService.processInput(mockRobotRequest);

        assertThat(robotResponse.getRobots().size()).isEqualTo(3);
        assertThat(robotResponse.getMessage()).isEqualTo("OK");

        assertThat(robotResponse.getRobots().get(0).getPosition()).isEqualTo("11E");
        assertThat(robotResponse.getRobots().get(0).isLost()).isEqualTo(false);

        assertThat(robotResponse.getRobots().get(1).getPosition()).isEqualTo("33N");
        assertThat(robotResponse.getRobots().get(1).isLost()).isEqualTo(true);

        assertThat(robotResponse.getRobots().get(2).getPosition()).isEqualTo("23S");
        assertThat(robotResponse.getRobots().get(2).isLost()).isEqualTo(false);

        assertThat(robotService.getDangerCoordinates().size()).isEqualTo(1);
        assertThat(robotService.getDangerCoordinates().get(0)).isEqualTo("33");
    }


    @Test
    public void oneRobot() {
        Robot r1 = new Robot("11E", "RFRFRFRF");
        when(mockRobotRequest.getRobots()).thenReturn(Arrays.asList(r1));
        when(mockRobotRequest.getWorld()).thenReturn(new RobotWorld(5,3));

        RobotResponse robotResponse = robotService.processInput(mockRobotRequest);

        assertThat(robotResponse.getRobots().size()).isEqualTo(1);
        assertThat(robotResponse.getMessage()).isEqualTo("OK");

        assertThat(robotResponse.getRobots().get(0).getPosition()).isEqualTo("11E");
        assertThat(robotResponse.getRobots().get(0).isLost()).isEqualTo(false);
        assertThat(robotService.getDangerCoordinates().size()).isEqualTo(0);


    }

    @Test
    public void oneRobotOutOfBounds() {
        Robot r1 = new Robot("32N", "FRRFLLFFRRFLL");
        when(mockRobotRequest.getRobots()).thenReturn(Arrays.asList(r1));
        when(mockRobotRequest.getWorld()).thenReturn(new RobotWorld(5,3));

        RobotResponse robotResponse = robotService.processInput(mockRobotRequest);

        assertThat(robotResponse.getRobots().size()).isEqualTo(1);
        assertThat(robotResponse.getMessage()).isEqualTo("OK");

        assertThat(robotResponse.getRobots().get(0).getPosition()).isEqualTo("33N");
        assertThat(robotResponse.getRobots().get(0).isLost()).isEqualTo(true);
        assertThat(robotService.getDangerCoordinates().size()).isEqualTo(1);
        assertThat(robotService.getDangerCoordinates().get(0)).isEqualTo("33");
    }

    @Test
    public void oneRobotOutOfBoundsNorth() {
        Robot r1 = new Robot("33N", "FLL");
        when(mockRobotRequest.getRobots()).thenReturn(Arrays.asList(r1));
        when(mockRobotRequest.getWorld()).thenReturn(new RobotWorld(5,3));

        RobotResponse robotResponse = robotService.processInput(mockRobotRequest);

        assertThat(robotResponse.getRobots().size()).isEqualTo(1);
        assertThat(robotResponse.getMessage()).isEqualTo("OK");

        assertThat(robotResponse.getRobots().get(0).getPosition()).isEqualTo("33N");
        assertThat(robotResponse.getRobots().get(0).isLost()).isEqualTo(true);
        assertThat(robotService.getDangerCoordinates().size()).isEqualTo(1);
        assertThat(robotService.getDangerCoordinates().get(0)).isEqualTo("33");
    }

    @Test
    public void oneRobotOutOfBoundsSouth() {
        Robot r1 = new Robot("10S", "FLL");
        when(mockRobotRequest.getRobots()).thenReturn(Arrays.asList(r1));
        when(mockRobotRequest.getWorld()).thenReturn(new RobotWorld(5,3));

        RobotResponse robotResponse = robotService.processInput(mockRobotRequest);

        assertThat(robotResponse.getRobots().size()).isEqualTo(1);
        assertThat(robotResponse.getMessage()).isEqualTo("OK");

        assertThat(robotResponse.getRobots().get(0).getPosition()).isEqualTo("10S");
        assertThat(robotResponse.getRobots().get(0).isLost()).isEqualTo(true);
        assertThat(robotService.getDangerCoordinates().size()).isEqualTo(1);
        assertThat(robotService.getDangerCoordinates().get(0)).isEqualTo("10");
    }
}
