package com.jmdc.mars.robots.controller;

import com.jmdc.mars.robots.model.Robot;
import com.jmdc.mars.robots.model.RobotRequest;
import com.jmdc.mars.robots.model.RobotResponse;
import com.jmdc.mars.robots.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

/*@Validated
@Controller
@Path("/mars/robots")

 */
public class RobotController {
/*
    @Autowired
    private RobotService robotService;

    @POST
    @Consumes({APPLICATION_JSON})
    @Produces(APPLICATION_JSON)
    @Path("/processInput")
    public RobotResponse processInput(@Valid RobotRequest robotRequest) throws Exception{
        return robotService.processInput(robotRequest);
    }

*/
}
