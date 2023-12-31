package ru.moskovka.weatherbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherbotApplication {

	/**
	 * Точка входа
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(WeatherbotApplication.class, args);
	}

}
