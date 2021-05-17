package com.jmdc.mars.robots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// controllers - services
@ComponentScan(basePackages = {"com.jmdc.mars.robots.controller","com.jmdc.mars.robots.service"})
// jpa repos
@EnableJpaRepositories(basePackages = {"com.jmdc.mars.robots.dao"})
// data model
@EntityScan(basePackages = "com.jmdc.mars.robots.model")
@SpringBootApplication
public class RobotsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RobotsApplication.class, args);
	}

}
