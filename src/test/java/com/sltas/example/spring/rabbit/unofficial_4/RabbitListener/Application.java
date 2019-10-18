package com.sltas.example.spring.rabbit.unofficial_4.RabbitListener;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@EnableRabbit
@ComponentScan
//@PropertySource(value = "classpath*:resources/mq.properties")
public class Application {
	
	private static Logger logger = LoggerFactory.getLogger(Application.class.getName());
	
    public static void main(String[] args) throws Exception{
    	
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        
        for (int i = 0; i < 20; i++) {
			
       	 rabbitTemplate.convertAndSend("sltas.direct.exchange", "sltas.info", "message before "+i, message -> {
                //使用lamdba的语法
                  MessageProperties properties = new MessageProperties();
                  properties.getHeaders().put("desc","消息发送");
                  properties.getHeaders().put("type",10);

                  Message messageafter = new Message(message.getBody(),properties);
                  return messageafter;
                  
              });
       	 
       	 rabbitTemplate.convertAndSend("sltas.direct.exchange", "sltas.debug", "message before "+i, message -> {
                //使用lamdba的语法
                  MessageProperties properties = new MessageProperties();
                  properties.getHeaders().put("desc","消息发送");
                  properties.getHeaders().put("type",10);

                  Message messageafter = new Message(message.getBody(),properties);
                  return messageafter;
                  
              });
       	 
       	 TimeUnit.SECONDS.sleep(10);
       	
		}
        
        logger.info("rabbit service startup");
        TimeUnit.SECONDS.sleep(60);
        context.close();
    }
}
