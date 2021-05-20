package com.jmdc.mars.robots.controller;

import com.jmdc.mars.robots.model.RobotRequest;
import com.jmdc.mars.robots.model.RobotResponse;
import com.jmdc.mars.robots.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(path="/mars/robots")
public class RobotControllerSp {

    @Autowired
    private RobotService robotService;

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/processInput",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public RobotResponse processInput(@Valid @RequestBody RobotRequest robotRequest) {
        return robotService.processInput(robotRequest);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/lostrobots",
            produces = MediaType.APPLICATION_JSON_VALUE

    )
    public RobotResponse findLostRobots() {
        return robotService.findLostRobots();
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/processInput",
            produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.TEXT_PLAIN_VALUE
    )
    public String processInputText(@Valid @RequestBody String robotRequestText) {
        return robotService.processInputText(robotRequestText);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/deleteDP"
    )
    public String resetDangerPoints() {
        robotService.deleteAllDangerPoints();
        return "OK";
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/deleteAll"
    )
    public String resetProcessedRobots() {
       robotService.deleteAllProcessedRobots();
       return "OK";
    }
}
