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
import com.rabbitmq.client.ShutdownSignalException;

/**
 * <p>
 * Title: Example8_Nack_Reject_Recv
 * </p>
 * <p>
 * Description: 手动提交
 * </p>
 * <p>
 * </p>
 * 
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 * 
 */
public class Example9_Nack_Reject_Recv {

	private static Logger logger = LoggerFactory.getLogger(Example5_ReceiveLogsTopic.class);

	private static final String EXCHANGE_NAME = "topic_logs";
	
	private static final String bindingKey = "*.info";
	
	private static final String QUEUE_NAME = "topic_queue";

	public static void main(String[] args) {
		
		try {
			
			String uuid = UUID.randomUUID().toString();
			MDC.put("destination", uuid);

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("127.0.0.1");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.basicQos(1);
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, bindingKey);
			
			logger.info(" [*] Waiting for messages. bindingKey : '{}' ",bindingKey);

			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					MDC.put("destination", uuid);
					String message = new String(body, "UTF-8");
					logger.info(" [x] Received routingKey : '{}' message : '{}' properties : '{}'",envelope.getRoutingKey(), message, properties);
					
//					channel.basicAck(envelope.getDeliveryTag(), false);
//					channel.basicNack(envelope.getDeliveryTag(), false, true);
					channel.basicReject(envelope.getDeliveryTag(), true);
					channel.basicReject(envelope.getDeliveryTag(), true);
				}
				
				/**
				 * 使用手动确认时，重要的是要考虑确认的线程。如果它与接收传递的线程不同（例如，Consumer＃handleDelivery 委托传递处理到另一个线程），
				 * 则将多个参数设置为true进行确认是不安全的，并且将导致双重确认，因此会导致通道级协议异常关闭频道。一次确认一条消息可能是安全的。
				 */
				@Override
			    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
			        logger.info("===========通道级协议异常关闭频道============");
			    }
				
			};
			channel.basicConsume(QUEUE_NAME, false, consumer);
			
		} catch (Exception e) {
			logger.error(" [x] error : '{}'",e);
		}
		
		
		
		
		

	}

}
