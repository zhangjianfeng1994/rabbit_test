package com.sltas.example.amqp.rabbit;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * <p>
 * Title: Example2_Task
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
public class Example2_Task {

	private static Logger logger = LoggerFactory.getLogger(Example2_Task.class);

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		
		for(int i = 0; i<200; i++){
			String message = "Hello World! === "+ i;
			channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
			logger.info(" [x] Sent '{}'", message);
		}
		
		TimeUnit.SECONDS.sleep(1000);
		
		channel.close();
		connection.close();

	}

}
