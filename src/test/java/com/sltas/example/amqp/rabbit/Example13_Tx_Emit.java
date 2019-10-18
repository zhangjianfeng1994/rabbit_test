package com.sltas.example.amqp.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP.Tx.CommitOk;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * <p>
 * Title: Example13_Tx_Recv.java
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
 * @date 2018年9月19日 下午4:37:01  
 */
public class Example13_Tx_Emit {

	private static Logger logger = LoggerFactory.getLogger(Example13_Tx_Emit.class);

	private static final String EXCHANGE_NAME = "direct_logs";
	
	private static final String routingKey = "info";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

		channel.txSelect();
		
		for(int i = 0 ; i < 10 ; i++){
			String message = "Hello World!"+i;
			channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
			logger.info(" [x] Sent '{}' routingKey '{}'", message,routingKey);
			Thread.sleep(10000l);
			CommitOk ok = channel.txCommit();
			logger.info("{}",ok);
//			channel.txRollback();
			
		}
		
		channel.close();
		connection.close();

	}

}
