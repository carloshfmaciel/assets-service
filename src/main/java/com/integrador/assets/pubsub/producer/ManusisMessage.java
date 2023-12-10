package com.integrador.assets.pubsub.producer;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.integrador.assets.constants.ManuSisMessageAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManusisMessage implements Serializable {

	private static final long serialVersionUID = 6773716242887246986L;

	private String id;
	
	private ManuSisMessageAction action;
	
	private LocalDateTime currentTimeStamp;

}
