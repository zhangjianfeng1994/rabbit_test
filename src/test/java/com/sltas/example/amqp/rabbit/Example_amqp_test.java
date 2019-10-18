package com.sltas.example.amqp.rabbit;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


/**
 * <p>
 * Title: Example_amqp_test.java
 * </p>
 * <p>
 * Description: amqp预构建
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年9月26日 上午10:09:06  
 */
public class Example_amqp_test {

	private static Logger logger = LoggerFactory.getLogger(Example_amqp_test.class);

	private static final String EXCHANGE_NAME = "direct_logs";
	
	private static final String bindingKey = "info";
	
	private static final String QUEUE_NAME = "direct_queue";

	public static void main(String[] args) throws Exception {
		
		String uuid = UUID.randomUUID().toString();
		MDC.put("destination", uuid);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT.getType(),true);
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//		String queueName = channel.queueDeclare().getQueue();
		//使用交换器绑定队列 
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, bindingKey);
		
		logger.info(" [*] Waiting for messages. bindingKey : '{}' ",bindingKey);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				MDC.put("destination", uuid);
				String message = new String(body, "UTF-8");
				logger.info(" [x] Received routingKey : '{}' message : '{}'",envelope.getRoutingKey(), message);
				
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);

	}

}
