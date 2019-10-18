package com.sltas.example.spring.rabbit.unofficial_3.RabbitListenerConfigurer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	public void handleMessage(byte[] message){
		logger.info("消费消息");
		logger.info("{}",new String(message));
    }

}
