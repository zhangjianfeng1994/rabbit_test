package com.framework.rabbit.xml;

import org.springframework.amqp.support.ConsumerTagStrategy;

public class ConsumerTag implements ConsumerTagStrategy{

	static int count = 1;
	
	@Override
	public String createConsumerTag(String queue) {
		return "order_queue_"+(++count);
	}

}
