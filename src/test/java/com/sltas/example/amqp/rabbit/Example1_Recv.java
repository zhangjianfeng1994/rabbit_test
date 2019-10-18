package com.sltas.example.amqp.rabbit;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * <p>
 * Title: Example1
 * </p>
 * <p>
 * Description: “Hello World！”
 * </p>
 * <p>
 * RabbitMQ说多种协议。本教程使用AMQP 0-9-1，它是一种开放的，通用的消息传递协议。RabbitMQ有许多不同语言的客户端。我们将使用RabbitMQ提供的Java客户端。
 * </p>
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 *  
 */
public class Example1_Recv {
	
	private static Logger logger = LoggerFactory.getLogger(Example1_Recv.class);
	
	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) throws Exception {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");// 主机名
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    
	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    logger.info(" [*] Waiting for messages. ");

	    /**
	     * 建立消费者
	     * @param consumerTag 	消费标签，消费者标签与消费者相关
	     * @param envelope 		信封包装数据
	     * @param properties	属性内容头数据
	     * @param body			主体消息体（不透明的、特定于客户端的字节数组）
	     * 如果消费者在处理消息时遇到了输入/输出错误，则抛出IOException
	     * @see信封
	     * 
	     * void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties,  byte[] body) throws IOException;
	     */
	    Consumer consumer = new DefaultConsumer(channel) {
	      @Override
	      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
	          throws IOException {
	    	logger.info("==========消费者one=========");
	    	//消费标签，消费者标签与消费者相关
	    	logger.info(" consumerTag : {} "	, consumerTag);
	    	logger.info(" envelope : {} "	, envelope);
	    	logger.info(" properties : {} "	, properties);
	        String message = new String(body, "UTF-8");
	        logger.info(" [x] Received '{}'"	, message);
	        
	      }
	    };
	    
	    Consumer consumer1 = new DefaultConsumer(channel) {
		      @Override
	      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
	          throws IOException {
	    	logger.info("==========消费者two=========");
	    	//消费标签，消费者标签与消费者相关
	    	logger.info(" consumerTag : {} "	, consumerTag);
	    	logger.info(" envelope : {} "	, envelope);
	    	logger.info(" properties : {} "	, properties);
	        String message = new String(body, "UTF-8");
	        logger.info(" [x] Received '{}'"	, message);
	        
	      }
	    };
	    
	    /**
	     * 绑定消费者
	     * 
	     * 启动一个非无地、非排他的消费者
	     * 注册服务器生成的consumerTag。
	     * @param queue 排队等待队列的名称 如果服务器应该考虑消息
	     * @param autoAck true 承认一次交付;如果服务器应该期望 明确的确认
	     * @param callback 回调一个面向消费者对象的接口返回由服务器生成的消费标签
	     * @throws . io .如果遇到错误，IOException
	     * @see com.rabbitmq.client.AMQP.Basic.Consume
	     * @see com.rabbitmq.client.AMQP.Basic.ConsumeOk
	     * @see basicconsumer（String、boolean、String、boolean、boolean、Map、消费者）
	     * 
	     * String basicConsume(String queue, boolean autoAck, Consumer callback) throws IOException;
	     */
	    channel.basicConsume(QUEUE_NAME, true, consumer);
	    channel.basicConsume(QUEUE_NAME, true, consumer1);
		
	}
	
}
