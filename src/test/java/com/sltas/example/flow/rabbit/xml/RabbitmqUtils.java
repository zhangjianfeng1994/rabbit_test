package com.sltas.example.flow.rabbit.xml;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.connection.CorrelationData.Confirm;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sltas.example.flow.rabbit.xml.RefundPojo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_rabbit.xml"})
public class RabbitmqUtils extends AbstractJUnit4SpringContextTests{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("rabbitTemplate")
	private RabbitTemplate rabbitTemplate;
	
	@Test
	public void threadSend(){
		
//		for(int i = 0 ; i < 30 ; i ++){
//			new Thread(()->{
//				 send();
//			}).start();
//		}
		 
		try {
			TimeUnit.SECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
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
	
	@Test
	public void send(){
		
		try {
			for(int i = 0 ; i < 1 ; i++){
	        	String uuid = UUID.randomUUID().toString();
	        	
	        	RefundPojo pojo = new RefundPojo();
	        	pojo.setId(uuid);
	        	pojo.setCreateTime(new Date());
	        	
	        	CorrelationData correlationData = new CorrelationData(uuid);
	        	
	        	try {
	        		rabbitTemplate.convertAndSend("order.refund.execute.exchange", "order.refund.execute.routingKey", pojo, message -> {
	   		         //使用lamdba的语法
	   		           MessageProperties properties = message.getMessageProperties();
	   		           properties.getHeaders().put("CorrelationData",uuid);
	   		           int priority = new Random().nextInt(5);
	   		           properties.setPriority(priority);
	   		           logger.info("生产者：[{}]============={}",uuid,message);
	   		           
//	   		           logger.info("{}",new String(message.getBody()));
//	   		           
//	   		           ObjectMapper mapper = new ObjectMapper();
//	   		           RefundPojo pojo1 = null;
//					try {
//						pojo1 = mapper.readValue(message.getBody(), RefundPojo.class);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//	   		           System.out.println(pojo1);
	   		           
		   		       return message;
	   		        
	   		       },correlationData);
	        		
	        		Confirm confirm = correlationData.getFuture().get(10, TimeUnit.SECONDS);
	        		logger.info("{}=========={}",confirm,correlationData.getReturnedMessage());
	        		
				} catch (Exception e) {
//					logger.error("=====异常消息：==============rabbitTemplate.convertAndSend========================");
//					logger.error("=====异常消息：==============rabbitTemplate.convertAndSend========================");
					logger.error("=====异常消息：==============rabbitTemplate.convertAndSend========================{}",e,e);
				}
//				TimeUnit.SECONDS.sleep(10);
			}
			
			TimeUnit.SECONDS.sleep(10000);
		} catch (Exception e) {
			
			/**
			 * 需要对该条信息进行回退操作，因入队失败
			 * 错误原因为，rabbitmq 数据源异常导致
			 */
			
			//需要对该条信息进行回退操作，因入队失败
			
			// TODO: handle exception
			
		}
		
//		try {
//			TimeUnit.SECONDS.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		
	} 

}
