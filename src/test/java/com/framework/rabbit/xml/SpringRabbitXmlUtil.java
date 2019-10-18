package com.framework.rabbit.xml;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpringRabbitXmlUtil extends AbstractJUnit4SpringContextTests{

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("connectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Autowired
	@Qualifier("rabbitAdmin")
	private RabbitAdmin rabbitAdmin;
	
	@Autowired
	@Qualifier("rabbitTemplate")
	private RabbitTemplate rabbitTemplate;
	
	
	@Test
	public void cachingConnectionFactory() {
		
		try {
			
			logger.info("{}",connectionFactory);
			logger.info("{}",rabbitAdmin);
			logger.info("{}",rabbitTemplate);
			
//			for(int i = 0 ; i < 10 ; i++){
//				rabbitTemplate.convertAndSend("sltas.direct.exchange", "sltas.info.routingKey", "message before", message -> {
//		         //使用lamdba的语法
//		           MessageProperties properties = new MessageProperties();
//		           properties.getHeaders().put("desc","消息发送");
//		           properties.getHeaders().put("type",10);
//		           int priority = new Random().nextInt(5);
//		           properties.setPriority(priority);
//		           logger.info("消息的优先级是 : {}",priority);
//
//		           Message messageafter = new Message("message after_sltas.info".getBytes(),properties);
//		           return messageafter;
//		       });
//			}

			
			TimeUnit.SECONDS.sleep(1000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
