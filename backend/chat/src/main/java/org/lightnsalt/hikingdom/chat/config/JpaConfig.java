package org.lightnsalt.hikingdom.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories("org.lightnsalt.hikingdom.chat.repository.mysql")
public class JpaConfig {
}
