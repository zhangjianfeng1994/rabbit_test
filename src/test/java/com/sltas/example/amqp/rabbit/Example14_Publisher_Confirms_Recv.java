package com.sltas.example.amqp.rabbit;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
 * Title: Example14_Publisher_Confirms_Recv.java
 * </p>
 * <p>
 * Description: 流媒体轻量级发布者确认
 * 
 * 在这种情况下使用事务有两个问题。第一个是它们阻塞：发布者必须等待代理处理每个消息。Â知道所有可能除了最后一个消息被成功处理的消息通常都是过于强大的保证; 
 * 如果发布者知道当经纪人去世时尚未处理哪些消息就足够了。第二个问题是事务处理不必要：每次提交都需要fsync（），这需要花费大量时间才能完成。
 * 输入确认：一旦通道进入确认模式，代理将在处理消息时确认消息。Â由于这是异步完成的，生产者可以流式发布而不是等待代理，代理可以有效地批量磁盘写入。 
 * 
 * 完整代码可在此处获得。在继续之前，值得一提的是，运行此操作大约需要2秒钟。它比事务代码快100多倍。
 * 代码做了什么？它首先声明一个将保存迄今未经证实的消息的ID的集合。然后，它将通道设置为确认模式并将AckListener附加到通道。Â在发布消息时，会将它们添加到集合中; 
 * 同时，AckListener在接收确认时从集合中删除消息。最后，生产者等待确认所有消息。Â该组始终保存在发生故障时需要重新传输的消息。
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年9月19日 下午5:05:04  
 */
public class Example14_Publisher_Confirms_Recv {

	private static Logger logger = LoggerFactory.getLogger(Example14_Publisher_Confirms_Recv.class);

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
		channel.basicQos(1);
		
		logger.info(" [*] Waiting for messages. bindingKey : '{}' ",bindingKey);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				MDC.put("destination", uuid);
				String message = new String(body, "UTF-8");
				logger.info(" [x] Received routingKey : '{}' message : '{}'",envelope.getRoutingKey(), message);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		};
		channel.basicConsume(queueName, true, consumer);

	}

}
