package com.antifraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan({"com.antifraud", "com.antifraud.controller"})
@PropertySource("classpath:application.properties")
public class LearnServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearnServiceApplication.class, args);
	}
}
