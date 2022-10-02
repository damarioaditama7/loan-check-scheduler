package com.example.loancheckscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LoanCheckSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanCheckSchedulerApplication.class, args);
	}

}
