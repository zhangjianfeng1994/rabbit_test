package com.sltas.flow.rabbit.service;

import com.sltas.flow.rabbit.dto.MessageProperties;

@FunctionalInterface
public interface MessagePreprocessor {

	MessageProperties createMessage();
	
}
