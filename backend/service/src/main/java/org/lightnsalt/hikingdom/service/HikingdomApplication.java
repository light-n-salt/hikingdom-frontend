package org.lightnsalt.hikingdom.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "org.lightnsalt.hikingdom")
@EnableEurekaClient
@EnableJpaAuditing
@EntityScan({"org.lightnsalt.hikingdom.domain.entity"})
public class HikingdomApplication {

	public static void main(String[] args) {
		SpringApplication.run(HikingdomApplication.class, args);
	}

}
