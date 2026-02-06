package com.roast.linc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LincApplication {

	public static void main(String[] args) {
		SpringApplication.run(LincApplication.class, args);
	}

}
