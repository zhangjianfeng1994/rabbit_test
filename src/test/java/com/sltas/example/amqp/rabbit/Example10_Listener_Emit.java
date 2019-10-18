package com.sltas.example.amqp.rabbit;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ReturnListener;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * <p>
 * Title: Example10_Listener_Emit
 * </p>
 * <p>
 * Description: 监听操作
 * </p>
 * <p>
 * </p>
 * 
 * 例如，如果客户端发布的消息的“mandatory”标志设置为未绑定到队列的“direct”类型的交换，则会调用返回监听器。
 * 
 * 触发条件： 1.创建了exchange但是没有为他绑定队列导致的消息未到达合适队列
 * 2.创建了exchange同时创建了queue，但是在将两者绑定的时候，
 * 使用的bindingKey和消息发布者使用的rountingKey不一致导致的消息未到达合适队列
 * 
 * RabbitMQ中监听器有ReturnListener、ConfirmListener、ShutdownListener，
 * 本练习中使用ReturnListener，在发布消息时设置mandatory等于true，
 * 监听消息是否有相匹配的队列，没有时ReturnListener将执行handleReturn方法，消息将返给发送者
 * 
 * 设置mandatory=true，当路由不到队列时返回给消息发送者，在return监听器中接收
 * 设置immediate=true，当路由不到消费者时返回，3.0以后版本已废弃，会影响镜像队列性能，建议采用消息TTL和DLX
 * 
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 * 
 */
public class Example10_Listener_Emit {

	private static Logger logger = LoggerFactory.getLogger(Example10_Listener_Emit.class);

	private static final String EXCHANGE_NAME = "exchange_listener";

	private static final String routingKey = "anonymous.info";

	public static void main(String[] args) {

		Connection connection = null;
		Channel channel = null;

		try {

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("127.0.0.1");
			connection = factory.newConnection();

			/**
			 * addShutdownListener（ShutdownListener listener）和
			 * removeShutdownListener（ShutdownListener listener），
			 * 用于管理任何侦听器，当对象转换为关闭状态时将触发这些侦听器
			 * 。请注意，将ShutdownListener添加到已关闭的对象将立即触发侦听器
			 */
			connection.addShutdownListener(new ShutdownListener() {
				@Override
				public void shutdownCompleted(ShutdownSignalException cause) {
					logger.error(" [x] ************************** cause : {}  ", cause, cause);
				}
			});

			channel = connection.createChannel();

			/**
			 * 如果发布的消息设置了“强制”标志，但无法路由，则代理会将其返回给发送客户端（通过AMQP.Basic.Return 命令）。
			 * 通知这样的回报，客户可以实现ReturnListener
			 * 接口并调用Channel.addReturnListener。如果客户端尚未为特定通道配置返回侦听器，
			 * 则将以静默方式删除关联的返回消息。 void
			 * com.rabbitmq.client.Channel.basicPublish(String exchange, String
			 * routingKey, boolean mandatory, boolean immediate, BasicProperties
			 * props, byte[] body) throws IOException
			 * 例如，如果客户端发布一条消息，其中“mandatory”标志设置为未绑定到队列的“direct”类型的交换，则将调用返回侦听器。
			 */
			channel.addReturnListener(new ReturnListener() {
				@Override
				public void handleReturn(int replyCode, String replyText, String exchange, String routingKey,
						BasicProperties properties, byte[] body) throws IOException {
					logger.error(" [x] replyCode : '{}' ", replyCode);
					logger.error(" [x] replyText : '{}' ", replyText);
					logger.error(" [x] exchange : '{}' ", exchange);
					logger.error(" [x] routingKey : '{}' ", routingKey);
					logger.error(" [x] properties : '{}' ", properties);
					logger.error(" [x] body : '{}' ", new String(body, "UTF-8"));
				}
			});

			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

			String message = "Hello World!";
			// channel.basicPublish(EXCHANGE_NAME, routingKey, null,
			// message.getBytes("UTF-8"));
			channel.basicPublish(EXCHANGE_NAME, routingKey, true, false, null, message.getBytes("UTF-8"));
			logger.info(" [x] Sent '{}' routingKey '{}'", message, routingKey);

			// channel.close();
			// connection.close();

		} catch (Exception e) {
			logger.error(" [x] =======catch==================== error : {}", e, e);
		} finally {
			/**
			 * AMQP连接和通道对象具有以下与关闭相关的方法：
			 * 
			 * addShutdownListener（ShutdownListener listener）和
			 * removeShutdownListener（ShutdownListener
			 * listener），用于管理任何侦听器，当对象转换为关闭状态时将触发这些侦听器
			 * 。请注意，将ShutdownListener添加到已关闭的对象将立即触发侦听器
			 * getCloseReason（），允许调查对象关闭的原因是什么 isOpen（），用于测试对象是否处于打开状态 close（int
			 * closeCode，String closeMessage），显式通知对象关闭
			 */
			try {
				if (channel != null && channel.isOpen()) {
					channel.close();
				}
				if (connection != null && connection.isOpen()) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error(" [x] =======finally============== error : {}", e2, e2);
			}
		}

		try {
			Thread.sleep(60000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
