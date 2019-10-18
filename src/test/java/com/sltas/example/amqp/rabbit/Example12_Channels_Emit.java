package com.sltas.example.amqp.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class Example12_Channels_Emit {

	private static Logger logger = LoggerFactory.getLogger(Example12_Channels_Emit.class);

	private static final String EXCHANGE_NAME = "direct_logs";
	
	private static final String routingKey = "info";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

		String message = "Hello World!";
		channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
		logger.info(" [x] Sent '{}' routingKey '{}'", message,routingKey);
		
		channel.close();
		connection.close();

	}

}
