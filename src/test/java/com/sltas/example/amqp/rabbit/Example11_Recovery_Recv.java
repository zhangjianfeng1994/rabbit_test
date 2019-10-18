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
import com.rabbitmq.client.Recoverable;
import com.rabbitmq.client.RecoveryListener;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * <p>
 * Title: Example4_ReceiveLogsDirect
 * </p>
 * <p>
 * Description: 恢复听众
 * </p>
 * <p>
 * </p>
 * 
 * http://www.rabbitmq.com/api-guide.html 官网
 * 
 * 客户端和RabbitMQ节点之间的网络连接可能会失败。RabbitMQ
 * Java客户端支持连接和拓扑（队列，交换，绑定和使用者）的自动恢复。许多应用程序的自动恢复过程遵循以下步骤：
 * 
 * 重新连接 还原连接侦听器 重新开放频道 还原通道侦听器
 * 
 * 恢复频道basic.qos设置，发行商确认和交易设置 拓扑恢复包括为每个通道执行的以下操作 重新申报交易所（预定义交易除外） 重新申报队列 恢复所有绑定
 * 恢复所有消费者 从Java客户端的4.0.0版开始，默认情况下启用自动恢复（因此也是拓扑恢复）。
 * 拓扑恢复依赖于实体（队列，交换，绑定，使用者）的每个连接缓存。当连接声明一个队列时，它将被添加到缓存中。当它被删除或计划删除（例如，因为它被自动删除）
 * 它将被删除。这个模型有一些局限在下面。
 * 
 * 要禁用或启用自动连接恢复，请使用factory.setAutomaticRecoveryEnabled（boolean）
 * 方法。以下片段显示了如何显式启用自动恢复（例如，对于Java 4.0.0之前的客户端）：
 * 
 * factory.setAutomaticRecoveryEnabled（true）;//将自动恢复的
 * 
 * 何时会触发连接恢复？
 * 
 * 自动连接恢复（如果已启用）将由以下事件触发：
 * 
 * 连接的I / O循环中抛出I / O异常 套接字读取操作超时 检测到错过的服务器心跳 连接的I / O循环中抛出任何其他意外异常 以先发生者为准。
 * 如果与RabbitMQ节点的初始客户端连接失败，则无法启动自动连接恢复。应用程序开发人员负责重试此类连接，记录失败尝试，实现重试次数限制等。
 * 这是一个非常基本的例子
 * 
 * 
 * 在可恢复的连接和通道上注册一个或多个恢复监听器是可能的。当连接恢复被启用时， ConnectionFactory
 * newconnection和Connection createchannel归还的连接将实现
 * com.rabbitmq.client。可恢复的，提供了两个具有相当描述性名称的方法：
 * 
 * addRecoveryListener removeRecoveryListener
 * 
 * 请注意，为了使用这些方法，您当前需要将连接和通道转换为可恢复的。
 * 
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 * 
 */
public class Example11_Recovery_Recv {

	private static Logger logger = LoggerFactory.getLogger(Example11_Recovery_Recv.class);

	private static final String EXCHANGE_NAME = "recovery_exchange";

	private static final String bindingKey = "recovery_key";

	public static void main(String[] args) throws Exception {

		String uuid = UUID.randomUUID().toString();
		MDC.put("destination", uuid);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");

//		factory.setAutomaticRecoveryEnabled(true);// 设置自动恢复  	4.0.0版开始，默认情况下启用自动恢复（因此也是拓扑恢复）
//		factory.setNetworkRecoveryInterval(10000);// 每隔10秒尝试恢复一次		如果由于异常导致恢复失败（例如，RabbitMQ节点仍然无法访问），则会在固定的时间间隔（默认为5秒）后重试。间隔可以配置：

		Connection connection = factory.newConnection();
		connection.addShutdownListener(new ShutdownListener() {
			@Override
			public void shutdownCompleted(ShutdownSignalException cause) {

				String hardError = "";
				String applInit = "";
				// 返回true如果这表示连接错误，或如果通道错误/则为false
				if (cause.isHardError()) {
					hardError = "connection";
				} else {
					hardError = "channel";
				}
				// 如果这个异常是由显式应用程序引起的，则返回true 行动;如果它是由经纪人或结果引起的 可检测的非故意应用程序故障
				if (cause.isInitiatedByApplication()) {
					applInit = "application";
				} else {
					applInit = "broker";
				}
				logger.error(" [x] Connectivity to MQ has failed.  It was caused by " + applInit + " at the "
						+ hardError + " level.  Reason received " + cause.getReason());
			}
		});

		((Recoverable) connection).addRecoveryListener(new RecoveryListener() {

			@Override
			public void handleRecoveryStarted(Recoverable recoverable) {
				logger.error(" [x] recoverable : {}",recoverable);
			}

			@Override
			public void handleRecovery(Recoverable recoverable) {
				if (recoverable instanceof Connection) {
					logger.error(" [x] Connection was recovered.");
				} else if (recoverable instanceof Channel) {
					int channelNumber = ((Channel) recoverable).getChannelNumber();
					logger.error(" [x] Connection to channel #" + channelNumber + " was recovered.");
				}
			}
		});

		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		String queueName = channel.queueDeclare().getQueue();
		// 使用交换器绑定队列
		channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);

		logger.info(" [*] Waiting for messages. bindingKey : '{}' ", bindingKey);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				MDC.put("destination", uuid);
				String message = new String(body, "UTF-8");
				logger.info(" [x] Received routingKey : '{}' message : '{}'", envelope.getRoutingKey(), message);

			}
		};
		channel.basicConsume(queueName, true, consumer);

	}

}
