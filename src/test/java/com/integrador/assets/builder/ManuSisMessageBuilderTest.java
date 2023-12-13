package com.integrador.assets.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.integrador.assets.constants.ManuSisMessageAction;
import com.integrador.assets.pubsub.producer.ManusisMessage;

@DisplayName("Writing assertions to ManuSisMessageBuilder")
public class ManuSisMessageBuilderTest {

	@Test
	void whenAllParametersAreInformedItMustReturnTheseInsideMessageObject() {
		
		String id = "1";
		ManuSisMessageAction action = ManuSisMessageAction.INSERT;
		
		ManusisMessage messageReturned = ManuSisMessageBuilder.toMessage(id, action);
		
		ManusisMessage messageExpected = ManusisMessage.builder().id(id).action(action).currentTimeStamp(LocalDateTime.now()).build();
		
		assertEquals(messageExpected.getId(), messageReturned.getId());
		assertEquals(messageExpected.getAction(), messageReturned.getAction());
	}

}
