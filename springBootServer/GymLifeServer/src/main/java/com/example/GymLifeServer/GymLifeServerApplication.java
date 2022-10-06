package com.example.GymLifeServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.GymLifeServer")

public class GymLifeServerApplication {

	public static void main(String[] args) {

		SpringApplication.run(GymLifeServerApplication.class, args);
	}

}
