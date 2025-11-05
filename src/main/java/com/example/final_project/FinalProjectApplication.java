package com.example.final_project;

import com.example.final_project.service.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jakarta.annotation.Resource;

@SpringBootApplication
public class FinalProjectApplication {

	@Resource
	FileStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return (args) -> {
			storageService.init();
		};
	}
}

