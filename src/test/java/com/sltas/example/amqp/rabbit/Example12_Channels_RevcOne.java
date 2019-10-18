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
 * Title: Example12_Channels_RevcOne.java
 * </p>
 * <p>
 * Description: 多消费者事例
 * 
 * 在测试过程中发现，在建立多通道 多消费者的时候，
 * 例如 ：
 * 	A Channel B Channel 下面均有两个 Consume
 * 
 * 平分的规则是 当最先启动的通道 A Channel 下面的两个 Consume都被分配到数据后，才会到达 B Channel，依次反复获得数据
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年9月19日 下午2:52:17  
 */
public class Example12_Channels_RevcOne {

	private static Logger logger = LoggerFactory.getLogger(Example12_Channels_RevcOne.class);

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

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
//		String queueName = channel.queueDeclare().getQueue();
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		//使用交换器绑定队列 
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, bindingKey);
		
		logger.info(" [*] Waiting for messages. bindingKey : '{}' ",bindingKey);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				MDC.put("destination", uuid);
				logger.info("==========消费者one=========");
				String message = new String(body, "UTF-8");
				logger.info(" [x] Received routingKey : '{}' message : '{}'",envelope.getRoutingKey(), message);
				
			}
		};
		
		Consumer consumer1 = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				MDC.put("destination", uuid);
				logger.info("==========消费者two=========");
				String message = new String(body, "UTF-8");
				logger.info(" [x] Received routingKey : '{}' message : '{}'",envelope.getRoutingKey(), message);
				
			}
		};
		
		channel.basicConsume(QUEUE_NAME, true, consumer);
		channel.basicConsume(QUEUE_NAME, true, consumer1);

	}

}
