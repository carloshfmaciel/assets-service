package com.integrador.assets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.integrador.assets.mongo.repository.AssetRepository;
import com.mongodb.client.MongoClient;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.mongo.MongoLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "5m")
public class CronConfig {

	@Bean
	LockProvider lockProvider(MongoClient mongoClient) {
		return new MongoLockProvider(mongoClient.getDatabase(AssetRepository.DATABASE_NAME));
	}
}
