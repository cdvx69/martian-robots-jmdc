package com.jmdc.mars.robots.it;

import com.jmdc.mars.robots.model.Robot;
import com.jmdc.mars.robots.model.RobotRequest;
import com.jmdc.mars.robots.model.RobotResponse;
import com.jmdc.mars.robots.model.RobotWorld;
import org.aspectj.weaver.World;
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
        RobotWorld world = new RobotWorld(3,5);
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

    }

    @Test
    public void cccitShouldProcessRobot() throws Exception {
    /*
        ParameterizedTypeReference<List<User>> personList = new ParameterizedTypeReference<List<User>>() {
        };

        // GET - ALL USERS
        ResponseEntity<List<User>> response = restTemplate.exchange("/api/v1/users", GET, null, personList);
        assertThat(response.getBody()).hasSize(0);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // POST - NEW USER
        User bareUser = new User(null, "", "", null, 0, "");
        HttpEntity<User> entity = new HttpEntity<>(bareUser, null);
        ResponseEntity<String> exchange = restTemplate.exchange("/api/v1/users", POST, entity, String.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);

        // GET - ALL USERS
        response = restTemplate.exchange("/api/v1/users", GET, null, personList);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get(0)).isEqualToIgnoringNullFields(bareUser);

        // GET BY USER BY ID=1
        ResponseEntity<User> getUserByIdResponse = restTemplate
                .exchange("/api/v1/users/1", GET, null, User.class);
        assertThat(getUserByIdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getUserByIdResponse.getBody()).isEqualToIgnoringNullFields(bareUser);

        // PUT - UPDATE USER BY ID=1
        User userToUpdate =
                new User(UUID.fromString("1"), "John", "Jones", User.Gender.MALE, 22, "john.jones@gmail.com");
        entity = new HttpEntity<>(userToUpdate, null);
        ResponseEntity<User> updateUserByIdResponse = restTemplate
                .exchange("/api/v1/users", PUT, entity, User.class);
        assertThat(updateUserByIdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // GET - USER BY ID=1
        getUserByIdResponse = restTemplate.exchange("/api/v1/users/1", GET, null, User.class);
        assertThat(getUserByIdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getUserByIdResponse.getBody()).isEqualToComparingFieldByField(userToUpdate);

        // POST - INSERT NEW USER
        User userToInsert = new User(null, "Nelson", "Mandela", User.Gender.MALE, 33, "nelson.mandela@gmail.com");
        entity = new HttpEntity<>(userToInsert, null);
        ResponseEntity<User> insertUserResponse = restTemplate
                .exchange("/api/v1/users", POST, entity, User.class, 1);
        assertThat(insertUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // GET - ALL USERS
        response = restTemplate.exchange("/api/v1/users", GET, null,
                personList);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get(0)).isEqualToComparingFieldByField(userToUpdate);
        assertThat(response.getBody().get(1)).isEqualToIgnoringNullFields(userToInsert);

        // DELETE - USER BY ID=1
        ResponseEntity<String> deleteUserResponse = restTemplate
                .exchange("/api/v1/users/1", DELETE, null, String.class);
        assertThat(deleteUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        response = restTemplate.exchange("/api/v1/users", GET, null,
                personList);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get(0)).isEqualToIgnoringNullFields(userToInsert);
*/
    }
}
