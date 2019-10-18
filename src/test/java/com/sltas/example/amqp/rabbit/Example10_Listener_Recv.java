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
 * Title: Example10_Listener_Recv
 * </p>
 * <p>
 * Description: 监听操作
 * </p>
 * <p>
 * </p>
 * 
 * 例如，如果客户端发布的消息的“mandatory”标志设置为未绑定到队列的“direct”类型的交换，则会调用返回监听器。
 * 
 * 触发条件： 
 * 1.创建了exchange但是没有为他绑定队列导致的消息未到达合适队列
 * 2.创建了exchange同时创建了queue，但是在将两者绑定的时候，使用的bindingKey和消息发布者使用的rountingKey不一致导致的消息未到达合适队列
 * 
 * RabbitMQ中监听器有ReturnListener、ConfirmListener、ShutdownListener，本练习中使用ReturnListener，在发布消息时设置mandatory等于true，
 * 监听消息是否有相匹配的队列，没有时ReturnListener将执行handleReturn方法，消息将返给发送者 
 * 
 * 设置mandatory=true，当路由不到队列时返回给消息发送者，在return监听器中接收 
 * 设置immediate=true，当路由不到消费者时返回，3.0以后版本已废弃，会影响镜像队列性能，建议采用消息TTL和DLX 
 * 
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 * 
 */
public class Example10_Listener_Recv {

	private static Logger logger = LoggerFactory.getLogger(Example10_Listener_Recv.class);

	private static final String EXCHANGE_NAME = "exchange_listener";
	
	private static final String bindingKey = "*.info";

	public static void main(String[] args) {
		
		try {
			
			String uuid = UUID.randomUUID().toString();
			MDC.put("destination", uuid);

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("127.0.0.1");
			//将自动恢复的 
			factory.setAutomaticRecoveryEnabled(true);
			//每隔10秒尝试恢复一次
			factory.setNetworkRecoveryInterval(1000);
			
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
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
				
				@Override
			    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
					logger.info("===========通道级协议异常关闭频道============");
			    }
				
			};
			channel.basicConsume(queueName, true, consumer);
			
		} catch (Exception e) {
			logger.error(" [x] error : '{}'",e);
		}
		
	}

}
