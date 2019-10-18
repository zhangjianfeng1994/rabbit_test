package com.sltas.example.amqp.rabbit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
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
 * Title: Example6_ReceiveLogsHeader
 * </p>
 * <p>
 * Description: Headers模式
 * </p>
 * <p>
 * </p>
 * 
 * HEADERS("headers")
 * 
 * Headers类型的exchange使用的比较少，它也是忽略routingKey的一种路由方式。
 * 是使用Headers来匹配的。Headers是一个键值对，可以定义成Hashtable。
 * 发送者在发送的时候定义一些键值对，接收者也可以再绑定时候传入一些键值对，两者匹配的话，则对应的队列就可以收到消息。
 * 匹配有两种方式all和any。这两种方式是在接收端必须要用键值"x-mactch"来定义。
 * all代表定义的多个键值对都要满足，而any则代码只要满足一个就可以了。
 * fanout，direct，topic exchange的routingKey都需要要字符串形式的，而headers exchange则没有这个要求，因为键值对的值可以是任何类型。
 * 
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 * 
 */
public class Example6_ReceiveLogsHeader {

	private static Logger logger = LoggerFactory.getLogger(Example6_ReceiveLogsHeader.class);

	private final static String EXCHANGE_NAME = "header-exchange";
	
	private final static String QUEUE_NAME = "header-queue";

	public static void main(String[] args) throws Exception {
		
		String uuid = UUID.randomUUID().toString();
		MDC.put("destination", uuid);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		//声明转发器和类型headers
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS,false,true,null);
		channel.queueDeclare(QUEUE_NAME,false, false, true,null);
		
		Map<String, Object> headers = new Hashtable<String, Object>();
		headers.put("x-match", "any");//all any
		headers.put("aaa", "01234");
		headers.put("bbb", "56789");
		//使用交换器绑定队列 
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME,"", headers);
		
		logger.info(" [*] Waiting for messages. ");

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
