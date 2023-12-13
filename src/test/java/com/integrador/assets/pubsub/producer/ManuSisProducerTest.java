package com.integrador.assets.pubsub.producer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;

@DisplayName("Writing assertions to ManuSisProducer")
public class ManuSisProducerTest {

	@InjectMocks
	private ManuSisProducer manuSisProducer;

	@Mock
	private PubSubTemplate pubSubTemplate;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(manuSisProducer, "manuSisTopic", "manusis.topic");
	}

	@Test
	void testCallSendToPubSubMethod() {
		when(pubSubTemplate.publish(anyString(), any(ManusisMessage.class))).thenReturn(mock(CompletableFuture.class));		
		manuSisProducer.sendToPubSub(mock(ManusisMessage.class));
		verify(pubSubTemplate, times(1)).publish(anyString(), any(ManusisMessage.class));
	}

}
