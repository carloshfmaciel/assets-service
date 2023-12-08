package com.integrador.assets.pubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;

@Component
public class ManuSisProducer {

	@Autowired
	private PubSubTemplate pubSubTemplate;

	@Value("${pubsub.topic.manusis}")
	private String manuSisTopic;

	public void sendToPubSub(String message) {
		pubSubTemplate.publish(manuSisTopic, message);
	}
}
