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
 * Title: Example3_ReceiveLogs
 * </p>
 * <p>
 * Description: 发布/订阅
 * </p>
 * <p>
 * </p>
 * 
 * RabbitMQ中消息传递模型的核心思想是生产者永远不会将任何消息直接发送到队列。实际上，生产者通常甚至不知道消息是否会被传递到任何队列。
 * 
 * 相反，生产者只能向交易所发送消息。交换是一件非常简单的事情。一方面，它接收来自生产者的消息，另一方面将它们推送到队列。交易所必须确切知道如何处理收到的消息。
 * 它应该附加到特定队列吗？它应该附加到许多队列吗？或者它应该被丢弃。其规则由交换类型定义 。
 * 
 * FANOUT("fanout"),	广播模式
 * 
 * 不处理路由键。你只需要简单的将队列绑定到交换机上。
 * 一个发送到交换机的消息都会被转发到与该交换机绑定的所有队列上。
 * 很像子网广播，每台子网内的主机都获得了一份复制的消息。Fanout交换机转发消息是最快的。
 * 
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 * 
 */
public class Example3_ReceiveLogs {

	private static Logger logger = LoggerFactory.getLogger(Example3_ReceiveLogs.class);

	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] args) throws Exception {
		
		String uuid = UUID.randomUUID().toString();
		MDC.put("destination", uuid);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
		//请注意，当只有一个客户端想要使用它时，这将是一种典型的声明方式：它不需要知名的名称，没有其他客户端可以使用它（独占），并且会自动清除（自动删除）。
		String queueName = channel.queueDeclare().getQueue();
		//使用交换器绑定队列 
		channel.queueBind(queueName, EXCHANGE_NAME, "");
		
		logger.info(" [*] Waiting for messages. ");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				MDC.put("destination", uuid);
				String message = new String(body, "UTF-8");
				logger.info(" [x] Received '{}'", message);
				
			}
		};
		channel.basicConsume(queueName, true, consumer);

	}

}
