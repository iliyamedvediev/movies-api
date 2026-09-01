package com.moviebase.movies;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoviesApplication {

	public static void main(String[] args) {

		System.out.println("Initialise FLYWAY!");

		try {
			Flyway flyway = Flyway.configure()
					.dataSource("jdbc:h2:file:./movies_db", "sa", "")
					.locations("classpath:db/migration")
					.load();

			flyway.migrate();
			System.out.println("FLYWAY Success!");
		} catch (Exception e) {
			System.out.println("Error FLYWAY: " + e.getMessage());
		}


		SpringApplication.run(MoviesApplication.class, args);
	}
}