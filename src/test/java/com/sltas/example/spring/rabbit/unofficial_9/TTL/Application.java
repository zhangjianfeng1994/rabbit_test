package com.sltas.example.spring.rabbit.unofficial_9.TTL;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@ComponentScan
public class Application {
	
	
	
    public static void main(String[] args) throws Exception{
    	
    	/**
    	 * RabbitMQ allows you to set TTL (time to live) for both messages and queues. This can be done using optional queue arguments or policies (the latter option is recommended). 
    	 * Message TTL can be enforced for a single queue, a group of queues or applied for individual messages.
    	 * RabbitMQ允许您为消息和队列设置TTL（生存时间）。 可以使用可选的队列参数或策略完成（推荐使用后一个选项）。 可以为单个队列，一组队列或单个消息应用消息TTL。
    	 * 
    	 * 如果同时制定了Message TTL，Queue TTL，则小的那个时间生效。
    	 * messageProperties.setExpiration("20000");
    	 * 
    	 * Map<String, Object> arguments = new HashMap<>();
    	 * arguments.put("x-message-ttl",30000);
    	 */
    	
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        byte[] body = "head.info.exchange!!!~~~".getBytes();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("json");
        //设置消息的过期时间
        messageProperties.setExpiration("20000");

        Message message = new Message(body,messageProperties);

        rabbitTemplate.send("sltas.direct.exchange","sltas.info",message);

        TimeUnit.SECONDS.sleep(30);

        context.close();
    }
}

