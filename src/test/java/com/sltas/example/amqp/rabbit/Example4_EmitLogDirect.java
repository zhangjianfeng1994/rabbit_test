package com.sltas.example.amqp.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * <p>
 * Title: Example4_EmitLogDirect
 * </p>
 * <p>
 * Description: 路由模式
 * </p>
 * <p>
 * </p>
 * 
 * 直接交换
 * 
 * 我们上一个教程中的日志记录系统向所有消费者广播所有消息。我们希望扩展它以允许根据消息的严重性过滤消息。例如，我们可能需要一个程序将日志消息写入磁盘以仅接收严重错误，而不是在警告或信息日志消息上浪费磁盘空间。
 * 我们使用的是扇出交换，它没有给我们太大的灵活性 - 它只能进行无意识的广播。
 * 我们将使用直接交换。直接交换背后的路由算法很简单 - 消息进入队列，其  绑定密钥与消息的路由密钥完全匹配。
 * 
 * DIRECT("direct"),	路由模式
 * 
 * 处理路由键。需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。
 * 这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键 “test”，
 * 则只有被标记为“test”的消息才被转发，不会转发test.aaa，也不会转发dog.123，
 * 只会转发test。
 * 
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 * 
 */
public class Example4_EmitLogDirect {

	private static Logger logger = LoggerFactory.getLogger(Example4_EmitLogDirect.class);

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
