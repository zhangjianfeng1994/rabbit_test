package com.sltas.example.amqp.rabbit;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * <p>
 * Title: Example2_Worker
 * </p>
 * <p>
 * Description: 工作队列 在工人之间分配任务（竞争消费者模式）
 * </p>
 * <p>
 * </p>
 * 
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 * 
 */
public class Example2_Worker {

	private static Logger logger = LoggerFactory.getLogger(Example2_Worker.class);

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] args) throws Exception {
		
		MDC.put("destination", UUID.randomUUID().toString());

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		final Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		logger.info(" [*] Waiting for messages. ");

		channel.basicQos(1);

		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");

				try {
					Thread.sleep(5000);
					logger.info(" [x] Received '{}'", message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					logger.info(" [x] Done");
					/**
					 * 当使用手动确认时，在消息传递和确认之间，到RabbitMQ节点的网络连接可能会失败。
					 * 连接恢复后，RabbitMQ将重置所有通道上的交付标签。这意味着basic.ack，basic.
					 * nack和basic.reject 与旧的交付标签将导致通道异常。 为了避免这种情况，RabbitMQ
					 * Java客户端跟踪并更新交付标签，使它们在恢复之间单调增长。
					 * 
					 * Channel.basicAck （basic.ack 用于肯定确认） Channel.basicNack
					 * （basic.nack 用于否定确认（注意：这是对AMQP
					 * 0-9-1的RabbitMQ扩展）nack是reject的升级版，将支持部分打回和全部打回的功能）
					 * Channel.basicReject （basic.reject 用于否定确认）
					 * 
					 * 然后将调整后的交付标签转换为RabbitMQ使用的标签。带有陈旧交付标签的确认将不会发送。
					 * 使用手动确认和自动恢复的应用程序必须能够处理重新投递。
					 * 
					 */
//					channel.basicAck(envelope.getDeliveryTag(), false);

					/**
					 * 
					 * 交货的负面确认和重新排列
					 * 
					 * 拒绝一个或多个接收到的消息。 从@link
					 * com.rabbitmq.client.AMQP.Basic.GetOk提供“交付标签”。 或{ @link
					 * com.rabbitmq.client.AMQP.Basic。GetOk方法包含要拒绝的消息。
					 * 
					 * @see com.rabbitmq.client.AMQP.Basic.Nack
					 * @param 从收到的@link com.rabbitmq.client.amqp.basic中标记标签。GetOk }或{ @link com.rabbitmq.client.AMQP.Basic.Deliver }
					 * @param 多重true来拒绝所有的消息，包括 提供的交货标签;不接受所提供的 交货标签。
					 * @param 如果被拒绝的消息（s）应该被重新请求，或丢弃
					 * @throws io .如果遇到错误，IOException
					 * 
					 * void basicNack(long deliveryTag, boolean multiple, boolean requeue) throws IOException;
					 */
//					channel.basicNack(envelope.getDeliveryTag(), false, false);
//					channel.basicNack(envelope.getDeliveryTag(), false, true);
				}
			}
		};
		channel.basicConsume(TASK_QUEUE_NAME, true, consumer);

		TimeUnit.SECONDS.sleep(1000);
		
	}

}
