package org.lightnsalt.hikingdom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HikingdomApplication {

	public static void main(String[] args) {
		SpringApplication.run(HikingdomApplication.class, args);
	}

}
