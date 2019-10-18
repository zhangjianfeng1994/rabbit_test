package com.sltas.example.amqp.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * <p>
 * Title: Example5_EmitLogTopic
 * </p>
 * <p>
 * Description: 主题模式
 * </p>
 * <p>
 * </p>
 * 
 * 主题交流
 * 
 * 发送到主题交换的消息不能具有任意  routing_key - 它必须是由点分隔的单词列表。单词可以是任何内容，但通常它们指定与消息相关的一些功能。一些有效的路由密钥示例：“ stock.usd.nyse ”，“ nyse.vmw ”，“ quick.orange.rabbit ”。路由密钥中可以包含任意数量的单词，最多可达255个字节。
 * 绑定密钥也必须采用相同的形式。主题交换背后的逻辑 类似于直接交换- 使用特定路由密钥发送的消息将被传递到与匹配绑定密钥绑定的所有队列。但是，绑定键有两个重要的特殊情况：
 * *（星号）可以替代一个单词。
 * ＃（hash）可以替换零个或多个单词。
 * 
 * TOPIC("topic"),		主题模式
 * 
 * 将路由键和某模式进行匹配。此时队列需要绑定要一个模式上。
 * 符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词。
 * 因此“audit.#”能够匹配到“audit.irs.corporate”，但是“audit.*” 只会匹配到“audit.irs”。
 * 
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 * 
 */
public class Example5_EmitLogTopic {

	private static Logger logger = LoggerFactory.getLogger(Example5_EmitLogTopic.class);

	private static final String EXCHANGE_NAME = "topic_logs";
	
	private static final String routingKey = "anonymous.info";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

		String message = "Hello World!";
		channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
		logger.info(" [x] Sent '{}' routingKey '{}'", message,routingKey);
		
		channel.close();
		connection.close();

	}

}
