package com.jmdc.mars.robots.dao;

import com.jmdc.mars.robots.model.RobotPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface RobotDao extends JpaRepository<RobotPath, Integer> {

    @Query(
        value = "SELECT * FROM ROBOTPATH RP WHERE RP.LOST='Y'",
        nativeQuery = true
    )
    Collection<RobotPath> findLostRobots();

}
