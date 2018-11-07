package com.music;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class MusicApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(MusicApplication.class, args);	
	}

	@Bean
	 public WebMvcConfigurer corsConfigurer() {
	       return new WebMvcConfigurerAdapter() {
	           @Override
	           public void addCorsMappings(CorsRegistry registry) {
	               registry.addMapping("/").allowedOrigins("http://localhost:4200");
	           }
	       };
	   }
}
