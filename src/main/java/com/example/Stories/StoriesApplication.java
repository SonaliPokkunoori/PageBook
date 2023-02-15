package com.example.Stories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StoriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoriesApplication.class, args);
	}

}
