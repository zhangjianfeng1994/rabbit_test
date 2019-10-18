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
 * Title: Example13_Tx_Emit.java
 * </p>
 * <p>
 * Description: TX事物的处理
 * 
 * 使用Tx保证交付
 * 
 * 在RabbitMQ的，持久性消息是一个应该在协商重新启动。
 * 这里的操作词是应该的，因为如果经纪人在有机会将消息写入磁盘之前关闭，消息仍然可能丢失。Â在某些情况下，这还不够，
 * 发布者需要知道消息是否得到了正确处理。直接的解决方案是使用事务，即提交每条消息。
 * 
 * https://www.rabbitmq.com/blog/2011/02/10/introducing-publisher-confirms/
 * 
 * 资料地址
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年9月19日 下午4:36:40  
 */
public class Example13_Tx_Recv {

	private static Logger logger = LoggerFactory.getLogger(Example13_Tx_Recv.class);

	private static final String EXCHANGE_NAME = "direct_logs";
	
	private static final String bindingKey = "info";

	public static void main(String[] args) throws Exception {
		
		String uuid = UUID.randomUUID().toString();
		MDC.put("destination", uuid);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		String queueName = channel.queueDeclare().getQueue();
		//使用交换器绑定队列 
		channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
		
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
		channel.basicConsume(queueName, true, consumer);

	}

}
