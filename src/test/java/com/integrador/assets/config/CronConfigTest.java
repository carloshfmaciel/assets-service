package com.integrador.assets.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.javacrumbs.shedlock.core.LockProvider;

@DisplayName("Writing assertions to CronConfig")
public class CronConfigTest {

	@InjectMocks
	private CronConfig cronConfig;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void itMustHaveLockProviderBean() {
		MongoClient mockMongoClient = mock(MongoClient.class);

		MongoDatabase mockMongoDatabase = mock(MongoDatabase.class);
		when(mockMongoClient.getDatabase(any())).thenReturn(mockMongoDatabase);

		MongoCollection mockMongoCollection = mock(MongoCollection.class);
		when(mockMongoDatabase.getCollection(anyString())).thenReturn(mockMongoCollection);

		LockProvider lockProvider = cronConfig.lockProvider(mockMongoClient);

		assertNotNull(lockProvider);
	}

}
