package com.integrador.assets.config;

import java.time.LocalDateTime;

import com.integrador.assets.constants.ManuSisMessageAction;
import com.integrador.assets.pubsub.producer.ManusisMessage;

public class ManuSisBuilder {

	public static ManusisMessage toMessage(String id, ManuSisMessageAction action) {
		return ManusisMessage.builder().id(id).action(action).currentTimeStamp(LocalDateTime.now()).build();
	}

}
