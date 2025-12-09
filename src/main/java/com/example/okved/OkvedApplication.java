package com.example.okved;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Главный класс Spring Boot приложения OKVED.
 *
 * <p>Документация оформлена в стиле JavaDoc.</p>
 */
@SpringBootApplication
@EnableScheduling
public class OkvedApplication {

	public static void main(String[] args) {
		SpringApplication.run(OkvedApplication.class, args);
	}
}