package com.jmdc.mars.robots.it;

import com.jmdc.mars.robots.model.Robot;
import com.jmdc.mars.robots.model.RobotRequest;
import com.jmdc.mars.robots.model.RobotResponse;
import com.jmdc.mars.robots.model.RobotWorld;
import org.aspectj.weaver.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpMethod.DELETE;


@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateUserIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        // resetting danger points and processed robots for each test
        restTemplate.exchange("/mars/robots/deleteDP", DELETE, null, String.class);
        restTemplate.exchange("/mars/robots/deleteAll", DELETE, null, String.class);
    }

    @Test
    public void itShouldFindZeroLostRobots() throws Exception {
        ResponseEntity<RobotResponse> response = restTemplate.exchange("/mars/robots/lostrobots", GET, null, RobotResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isNotBlank();
        assertThat(response.getBody().getMessage()).isEqualTo("found 0 lost robots");
    }

    @Test
    public void itShouldProcessRobot() throws Exception {
        // given
        Robot robot = new Robot("11E","RFRFRFRF", false);
        RobotWorld world = new RobotWorld(5,3);
        RobotRequest request = new RobotRequest(world, Arrays.asList(robot));

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        HttpEntity<RobotRequest> entity = new HttpEntity<>(request, requestHeaders);

        // when
        ResponseEntity<RobotResponse> responseEntity = restTemplate.exchange("/mars/robots/processInput", POST, entity, RobotResponse.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getMessage()).isEqualTo("OK");
        assertThat(responseEntity.getBody().getRobots()).hasSize(1);
        assertThat(responseEntity.getBody().getRobots().get(0).getPosition()).isEqualTo("11E");

    }

    @Test
    public void itShouldProcessLostRobot() throws Exception {
        // given
        Robot robot = new Robot("32N","FRRFLLFFRRFLL", false);
        RobotWorld world = new RobotWorld(5,3);
        RobotRequest request = new RobotRequest(world, Arrays.asList(robot));

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        HttpEntity<RobotRequest> entity = new HttpEntity<>(request, requestHeaders);

        // when
        ResponseEntity<RobotResponse> responseEntity = restTemplate.exchange("/mars/robots/processInput", POST, entity, RobotResponse.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getMessage()).isEqualTo("OK");
        assertThat(responseEntity.getBody().getRobots()).hasSize(1);
        assertThat(responseEntity.getBody().getRobots().get(0).getPosition()).isEqualTo("33N");
        assertThat(responseEntity.getBody().getRobots().get(0).isLost()).isEqualTo(true);

    }

    @Test
    public void itShouldProcessExampleInputRobots() throws Exception {


        ResponseEntity<RobotResponse> response = restTemplate.exchange("/mars/robots/lostrobots", GET, null, RobotResponse.class);

        // given
        Robot robot1 = new Robot("11E","RFRFRFRF", false);
        Robot robot2 = new Robot("32N","FRRFLLFFRRFLL", false);
        Robot robot3 = new Robot("03W","LLFFFLFLFL", false);
        RobotWorld world = new RobotWorld(5,3);
        RobotRequest request = new RobotRequest(world, Arrays.asList(robot1, robot2, robot3));

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        HttpEntity<RobotRequest> entity = new HttpEntity<>(request, requestHeaders);

        // when
        ResponseEntity<RobotResponse> responseEntity = restTemplate.exchange("/mars/robots/processInput", POST, entity, RobotResponse.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getMessage()).isEqualTo("OK");
        assertThat(responseEntity.getBody().getRobots()).hasSize(3);
        assertThat(responseEntity.getBody().getRobots().get(0).getPosition()).isEqualTo("11E");
        assertThat(responseEntity.getBody().getRobots().get(0).isLost()).isEqualTo(false);
        assertThat(responseEntity.getBody().getRobots().get(1).getPosition()).isEqualTo("33N");
        assertThat(responseEntity.getBody().getRobots().get(1).isLost()).isEqualTo(true);
        assertThat(responseEntity.getBody().getRobots().get(2).getPosition()).isEqualTo("23S");
        assertThat(responseEntity.getBody().getRobots().get(2).isLost()).isEqualTo(false);

    }
}
