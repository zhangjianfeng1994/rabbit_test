package com.framework.rabbit.annotation;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Application {
	
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) throws Exception {
		
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

//        CachingConnectionFactory connectionFactory = context.getBean(CachingConnectionFactory.class);
//        logger.info("{}",connectionFactory);
        
        logger.info("=====start up======");
        
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        
   	 	rabbitTemplate.convertAndSend("sltas.direct.exchange", "sltas.info.routingKey", "message before", message -> {
   	 	
   	 		
   	 	/**
   	 	 * 消息。服务器和应用程序之间传送的数据，本质上就是一段数据，由Properties和Payload(body)组成。
   	 	 * 
   	 	 * Delivery mode：是否持久化，如果未设置持久化，转发到queue中并未消费则重启服务或者服务宕机则消息丢失。
   	 	 * Headers：头信息，是由一个或多个健值对组成的，当固定的Properties不满足我们需要的时候，可以自己扩展。
   	 	 * 
   	 	 * Properties（属性）
   	 	 * content_type：传输协议
   	 	 * content_encoding：编码方式
   	 	 * priority：优先级
   	 	 * correlation_id：rpc属性，请求的唯一标识。
   	 	 * reply_to：rpc属性，
   	 	 * expiration：消息的过期时间
   	 	 * message_id：消息的id
   	 	 * timestamp：消息的时间戳
   	 	 * 
   	 	 * 如何保证消息的不丢失，三个地方做到持久化。
   	 	 * 
   	 	 * Exchange需要持久化。
   	 	 * Queue需要持久化。
   	 	 * Message需要持久化。
   	 	 */
           MessageProperties properties = new MessageProperties();
           properties.getHeaders().put("desc","消息发送");
           properties.getHeaders().put("type",10);
//           properties.setContentType("application/x-java-serialized-object");
//           properties.setContentType("application/json");

           Message messageafter = new Message("message after_sltas.info".getBytes(),properties);
           return messageafter;
           
       });
        

        
        TimeUnit.SECONDS.sleep(600);
        
        context.close();

    }

}
