package org.lightnsalt.hikingdom.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "org.lightnsalt.hikingdom")
@EntityScan({"org.lightnsalt.hikingdom.domain.entity", "org.lightnsalt.hikingdom.chat.entity"})
public class ChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}
}
