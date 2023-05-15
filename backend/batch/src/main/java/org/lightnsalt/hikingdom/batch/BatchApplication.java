package org.lightnsalt.hikingdom.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableBatchProcessing
@EnableJpaRepositories
@EntityScan(basePackages = {"org.lightnsalt.hikingdom.domain"})
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BatchApplication.class);
		ApplicationContext context = app.run(args);
		int exitCode = SpringApplication.exit(context);
		System.exit(exitCode);
	}

}
