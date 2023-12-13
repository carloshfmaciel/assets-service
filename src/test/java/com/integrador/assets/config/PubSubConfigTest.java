package com.integrador.assets.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.spring.pubsub.PubSubAdmin;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;

@DisplayName("Writing assertions to CronConfig")
public class PubSubConfigTest {

	@InjectMocks
	private PubSubConfig pubSubConfig;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);

		ReflectionTestUtils.setField(pubSubConfig, "projectId", "asset");
		ReflectionTestUtils.setField(pubSubConfig, "manusisTopic", "manusis.topic");
		ReflectionTestUtils.setField(pubSubConfig, "manusisSubscription", "manusis.subscription");
	}

	@Test
	void itMustHaveJacksonPubSubMessageConverterBean() {
		JacksonPubSubMessageConverter jacksonPubSubMessageConverter = pubSubConfig
				.jacksonPubSubMessageConverter(new ObjectMapper());
		assertNotNull(jacksonPubSubMessageConverter);
	}

	@Test
	void itMustReturnsNoCredentialsProvider() {
		CredentialsProvider credentialsProvider = pubSubConfig.credentialsProvider();
		assertNotNull(credentialsProvider);
	}

	@Test
	void whenCallManuSisTopicMethodItMustReturnsPubSubTopic() {

		try (MockedStatic<TopicName> mockStatic = mockStatic(TopicName.class)) {
			mockStatic.when(() -> TopicName.of(anyString(), anyString())).thenReturn(mock(TopicName.class));

			TopicAdminClient mockTopicAdminClient = mock(TopicAdminClient.class);
			Topic mockTopic = mock(Topic.class);

			when(mockTopicAdminClient.getTopic(any(TopicName.class))).thenReturn(mockTopic);

			Topic manuSisTopic = pubSubConfig.manuSisTopic(mockTopicAdminClient);

			assertNotNull(manuSisTopic);
		}
	}

	@Test
	void whenCallManuSisTopicAndItDoesntExistItMustReturnsPubSubTopic() {

		try (MockedStatic<TopicName> mockStatic = mockStatic(TopicName.class)) {
			mockStatic.when(() -> TopicName.of(anyString(), anyString())).thenReturn(mock(TopicName.class));

			TopicAdminClient mockTopicAdminClient = mock(TopicAdminClient.class);
			when(mockTopicAdminClient.getTopic(any(TopicName.class))).thenThrow(NullPointerException.class);

			Topic mockTopic = mock(Topic.class);
			when(mockTopicAdminClient.createTopic(any(TopicName.class))).thenReturn(mockTopic);

			Topic manuSisTopic = pubSubConfig.manuSisTopic(mockTopicAdminClient);

			assertNotNull(manuSisTopic);
		}
	}

	@Test
	void whenCallManuSisSubscriptionItMustReturnsPubSubSubscription() {
		PubSubAdmin mockPubSubAdmin = mock(PubSubAdmin.class);
		Subscription mockSubscription = mock(Subscription.class);
		when(mockPubSubAdmin.getSubscription(anyString())).thenReturn(mockSubscription);

		Subscription manuSisSubscription = pubSubConfig.manuSisSubscription(mockPubSubAdmin);

		assertNotNull(manuSisSubscription);
	}

	@Test
	void whenCallManuSisSubscriptionAndItDoesntExistItMustReturnsPubSubSubscription() {
		PubSubAdmin mockPubSubAdmin = mock(PubSubAdmin.class);
		when(pubSubConfig.manuSisSubscription(mockPubSubAdmin)).thenReturn(null);

		Subscription mockSubscription = mock(Subscription.class);
		when(mockPubSubAdmin.createSubscription(anyString(), anyString())).thenReturn(mockSubscription);

		Subscription manuSisSubscription = pubSubConfig.manuSisSubscription(mockPubSubAdmin);

		assertNotNull(manuSisSubscription);
	}

}
