package com.integrador.assets.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.spring.pubsub.PubSubAdmin;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;

@Configuration
public class PubSubConfig {

	@Value("${spring.cloud.gcp.pubsub.project-id}")
	private String projectId;

	@Value("${pubsub.topic.manusis}")
	private String manusisTopic;

	@Value("${pubsub.subscription.manusis}")
	private String manusisSubscription;

	@Bean
	JacksonPubSubMessageConverter jacksonPubSubMessageConverter(ObjectMapper objectMapper) {
		return new JacksonPubSubMessageConverter(objectMapper);
	}

	@Bean
	CredentialsProvider credentialsProvider() {
		return new NoCredentialsProvider();
	}

//	@Bean
//	MessageChannel pubsubInputChannel() {
//		return new DirectChannel();
//	}

	@Bean
	Topic manuSisTopic(TopicAdminClient topicAdminClient) {
		try {
			return topicAdminClient.getTopic(TopicName.of(projectId, manusisTopic));
		} catch (Exception e) {
			return topicAdminClient.createTopic(TopicName.of(projectId, manusisTopic));
		}
	}

	@Bean
	Subscription mySubscription(PubSubAdmin pubSubAdmin) {
		Subscription subscription = pubSubAdmin.getSubscription(manusisSubscription);
		if (subscription == null) {
			return pubSubAdmin.createSubscription(manusisSubscription, manusisTopic);
		}

		return subscription;
	}
}
