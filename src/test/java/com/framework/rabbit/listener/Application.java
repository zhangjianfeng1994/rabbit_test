package com.framework.rabbit.listener;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableRabbit
public class Application {
	
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	
	public static void main(String[] args) throws Exception {
		
		/**
		 * 我们的系统大多拆分为分布式SOA，或者微服务，一套系统中包含了多个子系统服务，而一个子系统服务往往会去调用另一个服务，
		 * 而服务调用服务无非就是使用RPC通信或者restful，既然是通信，那么就有可能再服务器处理完毕后返回结果的时候挂掉，这个时候用户端发现很久没有反应，
		 * 那么就会多次点击按钮，这样请求有多次，那么处理数据的结果是否要统一呢？那是肯定的！尤其再支付场景。
		 *
 		 * 幂等性：就是用户对于同一操作发起的一次请求或者多次请求的结果是一致的，不会因为多次点击而产生了副作用。
 		 * 举个最简单的例子，那就是支付，用户购买商品后支付，支付扣款成功，但是返回结果的时候网络异常，此时钱已经扣了，用户再次点击按钮，
 		 * 此时会进行第二次扣款，返回结果成功，用户查询余额发现多扣钱了，流水记录也变成了两条．．．
		 */
		
		try {
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

//	        CachingConnectionFactory connectionFactory = context.getBean(CachingConnectionFactory.class);
//	        logger.info("{}",connectionFactory);
	        
	        logger.info("=====start up======");
	        
	        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
	        
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
			for(int i = 0 ; i < 1 ; i++){
	        	String uuid = UUID.randomUUID().toString();
				rabbitTemplate.convertAndSend("sltas.direct.exchange1", "sltas.info.routingKey1", "message before", message -> {
		         //使用lamdba的语法
		           MessageProperties properties = new MessageProperties();
		           properties.getHeaders().put("desc","消息发送");
		           properties.getHeaders().put("type",10);
		           int priority = new Random().nextInt(5);
		           properties.setPriority(priority);
		           logger.info("消息的优先级是 : {} 唯一标识： {}",priority,uuid);

		           Message messageafter = new Message("message after_sltas.info".getBytes(),properties);
		           return messageafter;
		       },new CorrelationData(uuid));
				
//				TimeUnit.SECONDS.sleep(10);
			}
	        
	        TimeUnit.SECONDS.sleep(600);
	        
	        context.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        

    }

}
