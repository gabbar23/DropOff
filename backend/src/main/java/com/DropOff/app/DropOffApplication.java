package com.DropOff.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.Random;

import static com.DropOff.app.utility.Const.RANDOM_LOWER_BOUND;
import static com.DropOff.app.utility.Const.RANDOM_UPPER_BOUND;

@SpringBootApplication
@RestController
@EnableWebSocket
public class DropOffApplication {

	/**
	 * Main method to run application
	 *
	 * @param args arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(DropOffApplication.class, args);
	}

	/**
	 * Home page message
	 *
	 * @return message to display on home page
	 */
	@GetMapping("/")
	String home() {
		return "Application running last updated on 9 April 2023";
	}
}
