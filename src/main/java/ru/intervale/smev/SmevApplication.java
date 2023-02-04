package ru.intervale.smev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class SmevApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmevApplication.class, args);
	}


}
