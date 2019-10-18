package com.sltas.example.amqp.rabbit;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * <p>
 * Title: Example8_BasicProperties_Emit
 * </p>
 * <p>
 * Description: 优先级队列
 * </p>
 * <p>
 * </p>
 * 
 * 队列queue的最大优先级
 * 
 * Map<String,Object> args = new HashMap<String,Object>();
 * args.put("x-max-priority", 10);
 * channel.queueDeclare("queue_priority", true, false, false, args);
 * 
 * 发送的消息中设置消息本身的优先级
 * 
 * AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
 * builder.priority(5);
 * AMQP.BasicProperties properties = builder.build();
 * channel.basicPublish("exchange_priority","rk_priority",properties,("messages").getBytes());
 *
 * PS：如果队列queue不设置成优先级队列，那么在消息中设置消息的优先级没有意义
 * 
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 * 
 */
public class Example8_BasicProperties_Emit {
	
	
	private static Logger logger = LoggerFactory.getLogger(Example8_BasicProperties_Emit.class);

	private static final String EXCHANGE_NAME = "exchange_priority";
	
	private static final String routingKey = "rk.priority";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("latitude",  51.5252949);
		headers.put("longitude", -0.0905493);
		
		Random ra =new Random();
		
		for(int i = 0 ; i < 10 ; i ++){
			
			/**
			 * 消息。服务器和应用程序之间传送的数据，本质上就是一段数据，由Properties和Payload(body)组成。
			 * 
			 * Delivery mode：是否持久化，如果未设置持久化，转发到queue中并未消费则重启服务或者服务宕机则消息丢失。
			 * Headers：头信息，是由一个或多个健值对组成的，当固定的Properties不满足我们需要的时候，可以自己扩展。
			 * 
			 * Properties（属性）
			 * content_type：传输协议
			 * content_encoding：编码方式
			 * priority：优先级
			 * correlation_id：rpc属性，请求的唯一标识。
			 * reply_to：rpc属性，
			 * expiration：消息的过期时间
			 * message_id：消息的id
			 * timestamp：消息的时间戳
			 * 
			 * 如何保证消息的不丢失，三个地方做到持久化。
			 * 
			 * Exchange需要持久化。
			 * Queue需要持久化。
			 * Message需要持久化。
			 */
			
			BasicProperties props = new AMQP.BasicProperties.Builder()
	        .contentType("text/plain")
	        .deliveryMode(2)//传递模式
	        .priority(ra.nextInt(10))//优先级
//	        .priority(i)//发送的消息中设置消息本身的优先级
	        .userId("guest")
	        .headers(headers)//自定义头信息
	        .expiration("60000")//过期时间 本条队列中的存活时间
	        .build();
			
			String message = "Hello World!";
			channel.basicPublish(EXCHANGE_NAME, routingKey, props, message.getBytes("UTF-8"));
			logger.info(" [x] Sent '{}' routingKey '{}'", message,routingKey);
			
		}
		
		channel.close();
		connection.close();

	}

	

}
