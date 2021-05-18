package com.jmdc.mars.robots.service;

import com.jmdc.mars.robots.dao.RobotDangerPointDao;
import com.jmdc.mars.robots.dao.RobotDao;
import com.jmdc.mars.robots.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class RobotService {

    @Autowired
    RobotDao robotDao;

    @Autowired
    RobotDangerPointDao dangerPointDao;

    @SuppressWarnings("RegExpDuplicateCharacterInClass")
    private static final String POSITION_PATTERN = "(\\d)(\\d)([N,S,W,E])";
    private RobotWorld robotWorld;
    private List<String> dangerCoordinates;

    public RobotService(RobotDao robotDao, RobotDangerPointDao dangerPointDao) {
        this.robotDao = robotDao;
        this.dangerPointDao = dangerPointDao;
    }

    public String processInputText(String text) {
        List<String> robotRequest = Arrays.asList(text.split("\n"));
        findLostRobots();
        return "hola";
    }

    public RobotResponse processInput(RobotRequest robotRequest) {

        // build coordinates map
        setWorld(robotRequest.getWorld());
        //
        List<Robot> calculatedRobotList = new ArrayList<>();
        for(Robot robot: robotRequest.getRobots()) {
            Robot robotResult = calculateFinalRobotPosition(robot);
            calculatedRobotList.add(robotResult);
        }

        // serialize to bbdd
        saveRobots(calculatedRobotList);
        saveDangerCoordinates();

        RobotResponse response = new RobotResponse();
        response.setMessage("OK");
        response.getRobots().addAll(calculatedRobotList);
        return response;
    }

    private void saveRobots(List<Robot> robots) {
        for(Robot robot: robots) {
            RobotPosition robotPosition = mapStringToRobotPosition(robot.getPosition());
            RobotPath rp = new RobotPath(String.valueOf(robotPosition.getRobotx()), String.valueOf(robotPosition.getRoboty()), robot.isLost(), Date.valueOf(LocalDateTime.now().toLocalDate()));
            robotDao.saveAndFlush(rp);
        }
    }

    private Robot calculateFinalRobotPosition(Robot robot) {
        for(int i=0; i< robot.getMovement().length() ; i++) {
            try {
                String move = String.valueOf(robot.getMovement().charAt(i));
                if("F".equals(move)) {
                    robot = changeRobotPosition(robot);
                }
                if(robot.isLost()) {
                    continue;
                }
                if("L".equals(move) || "R".equals(move)) {
                    robot = changeRobotOrientation(move, robot);
                }
            } catch (IllegalArgumentException iae) {
                robot.setMessage(iae.getMessage());
            }

        }
        return robot;
    }


    @Transactional
    private Robot changeRobotPosition(Robot robot) {
        RobotPosition robotPosition = mapStringToRobotPosition(robot.getPosition());
        RobotPosition.Orientation orientation = robotPosition.getOrientation();

        int newCoordY = 0;
        int newCoordX = 0;

        switch (orientation) {
            case NORTH:
            case SOUTH:
                newCoordY = robotPosition.getRoboty() + orientation.movement();
                newCoordX = robotPosition.getRobotx();
                break;

            case EAST:
            case WEST:
                newCoordX = robotPosition.getRobotx() + orientation.movement();
                newCoordY = robotPosition.getRoboty();
                break;
        }

        String newPosition = mapPositionToString(newCoordX, newCoordY, robotPosition.getOrientation().initial());
        if(robotWorld.isOutOfWorld(newCoordX, newCoordY)) {
            if(!getDangerCoordinates().contains(robotPosition.getStringCoordinates())) {
                robot.setLost(true);
//                dangerCoordinates.add(robotPosition.getStringCoordinates());
                saveDangerCoordinate(robotPosition.getRobotx(), robotPosition.getRoboty());

            }
        } else {
            robot.setPosition(newPosition);
        }

        return robot;
    }


    private Robot changeRobotOrientation(String move, Robot robot) {
        RobotPosition robotPosition = mapStringToRobotPosition(robot.getPosition());
        String coordinates = "NESW";
        int newPositionIdx;
        switch(move) {
            case "L":
                newPositionIdx = coordinates.indexOf(robotPosition.getOrientation().initial()) - 1;
                if(newPositionIdx < 0) {
                    robotPosition.setOrientation(RobotPosition.Orientation.orientationByName(String.valueOf(coordinates.charAt(coordinates.length()-1))));
                } else {
                    robotPosition.setOrientation(RobotPosition.Orientation.orientationByName(String.valueOf(coordinates.charAt(newPositionIdx))));
                }

                break;
            case"R":
                newPositionIdx = coordinates.indexOf(robotPosition.getOrientation().initial()) + 1;
                if(newPositionIdx == coordinates.length()) {
                    robotPosition.setOrientation(RobotPosition.Orientation.orientationByName(String.valueOf(coordinates.charAt(0))));
                } else {
                    robotPosition.setOrientation(RobotPosition.Orientation.orientationByName(String.valueOf(coordinates.charAt(newPositionIdx))));
                }
                break;
        }
        robot.setPosition(mapPositionToString(robotPosition));
        return robot;
    }
    
    

    private RobotPosition mapStringToRobotPosition(String position) {
        Pattern pattern = Pattern.compile(POSITION_PATTERN);
        Matcher matcher = pattern.matcher(position);
        if(matcher.find()) {
            return new RobotPosition(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), RobotPosition.Orientation.orientationByName(matcher.group(3)));
        }
        throw new IllegalArgumentException(String.format("position %s is not valid", position));
    }

    private String mapPositionToString (RobotPosition rp) {
        return mapPositionToString(rp.getRobotx(), rp.getRoboty(), rp.getOrientation().initial());
    }

    private String mapPositionToString(int x, int y, String orientation) {
        return String.valueOf(x).concat(String.valueOf(y).concat(orientation));
    }

    public List<RobotPath> findLostRobots () {
        Collection<RobotPath> lostRobots = robotDao.findLostRobots();
        return new ArrayList<>(lostRobots);
    }

    private void setWorld(RobotWorld robotWorld) {
        this.robotWorld = robotWorld;
    }

    public List<String> getDangerCoordinates_old() {
        if(dangerCoordinates == null) {
            dangerCoordinates = new ArrayList<>();
        }
        return dangerCoordinates;
    }

    public List<String> getDangerCoordinates() {
        List<RobotDangerPoint> dangerPoints = new ArrayList<>(dangerPointDao.findAll());
        if(!CollectionUtils.isEmpty(dangerPoints)) {
            return dangerPoints.stream().map(item -> item.getCoordx().concat(item.getCoordy())).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public void saveDangerCoordinates() {
        if(!CollectionUtils.isEmpty(dangerCoordinates)) {
            for (String coordinate : dangerCoordinates) {
                dangerPointDao.saveAndFlush(new RobotDangerPoint(coordinate.substring(0, 1), coordinate.substring(1), Date.valueOf(LocalDateTime.now().toLocalDate())));
            }
        }
    }

    public void saveDangerCoordinate(int x, int y){
        dangerPointDao.saveAndFlush(new RobotDangerPoint(String.valueOf(x), String.valueOf(y), Date.valueOf(LocalDateTime.now().toLocalDate())));
    }

}
