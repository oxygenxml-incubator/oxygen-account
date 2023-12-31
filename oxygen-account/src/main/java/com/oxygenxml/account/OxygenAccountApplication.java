package com.oxygenxml.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OxygenAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(OxygenAccountApplication.class, args);
	}

}
