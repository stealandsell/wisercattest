package com.petmanagement.petmanagement;

import com.petmanagement.petmanagement.configurer.CorsConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PetmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetmanagementApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new CorsConfigurer();
	}

}
