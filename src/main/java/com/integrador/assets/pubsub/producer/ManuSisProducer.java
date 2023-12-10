package com.integrador.assets.pubsub.producer;

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

	public void sendToPubSub(ManusisMessage manusisMessage) {
		pubSubTemplate.publish(manuSisTopic, manusisMessage);
	}
}