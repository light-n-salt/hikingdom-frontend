package org.lightnsalt.hikingdom.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories("org.lightnsalt.hikingdom.chat.repository.mongo")
public class MongoConfig {

	@Bean
	MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}
}
