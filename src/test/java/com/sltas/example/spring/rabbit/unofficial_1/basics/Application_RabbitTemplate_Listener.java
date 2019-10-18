package com.sltas.example.spring.rabbit.unofficial_1.basics;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 * Title: Application_RabbitTemplate_Listener.java
 * </p>
 * <p>
 * Description: 
 * 
 * 消息的消费
 * 
 * 使用容器的方式进行消费
 * 认识一个接口org.springframework.amqp.rabbit.listener.MessageListenerContainer，
 * 其默认实现类org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer。
 * MessageListenerContainer#setMessageListener方法，接收的参数类型
 * org.springframework.amqp.core.MessageListener或者org.springframework.amqp.rabbit.core.ChannelAwareMessageListener接口
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年9月26日 下午5:34:07  
 */
@ComponentScan
public class Application_RabbitTemplate_Listener {
	
	private static Logger logger = LoggerFactory.getLogger(Application_RabbitTemplate_Listener.class);

	public static void main(String[] args) throws Exception {
		
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application_RabbitTemplate_Listener.class);

        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        logger.info("RabbitTemplate : {}",rabbitTemplate);
        
        for (int i = 0; i < 20; i++) {
			
        	 rabbitTemplate.convertAndSend("sltas.direct.exchange", "sltas.info", "message before", message -> {
                 
                 //使用lamdba的语法
                   MessageProperties properties = new MessageProperties();
                   properties.getHeaders().put("desc","消息发送");
                   properties.getHeaders().put("type",10);

                   Message messageafter = new Message("message after_sltas.info".getBytes(),properties);
                   return messageafter;
                   
               });
        	 
        	 rabbitTemplate.convertAndSend("sltas.direct.exchange", "sltas.debug", "message before", message -> {
                 
                 //使用lamdba的语法
                   MessageProperties properties = new MessageProperties();
                   properties.getHeaders().put("desc","消息发送");
                   properties.getHeaders().put("type",10);

                   Message messageafter = new Message("message after_sltas.debug".getBytes(),properties);
                   return messageafter;
                   
               });
        	 
        	 TimeUnit.SECONDS.sleep(10);
        	
		}
        
        
        TimeUnit.SECONDS.sleep(30);
        
        context.close();

    }

}
