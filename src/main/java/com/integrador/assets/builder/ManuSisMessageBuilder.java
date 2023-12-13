package com.integrador.assets.builder;

import java.time.LocalDateTime;

import com.integrador.assets.constants.ManuSisMessageAction;
import com.integrador.assets.pubsub.producer.ManusisMessage;

public class ManuSisMessageBuilder {

	private ManuSisMessageBuilder() {
	}

	public static ManusisMessage toMessage(String id, ManuSisMessageAction action) {
		return ManusisMessage.builder().id(id).action(action).currentTimeStamp(LocalDateTime.now()).build();
	}

}
