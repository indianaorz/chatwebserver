package com.colur;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, ColorRepository colorRepository) {
		return (evt) -> Arrays.asList("00FF00,FF0000,0000FF,AABBCC, FFFFFF, 000000".split(",")).forEach(a -> {
			User user = userRepository.save(new User(a));
			colorRepository.save(new Color(user, a, "1/1/1"));
			colorRepository.save(new Color(user, a, "2/2/2"));
		});
	}

}