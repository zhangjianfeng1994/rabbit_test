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
 * Title: Application_RabbitTemplate_Emit.java
 * </p>
 * <p>
 * Description: 
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年9月26日 下午5:34:07  
 */
@ComponentScan
public class Application_RabbitTemplate_Emit3 {
	
	private static Logger logger = LoggerFactory.getLogger(Application_RabbitTemplate_Emit3.class);

	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application_RabbitTemplate_Emit3.class);

        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        logger.info("RabbitTemplate : {}",rabbitTemplate);
        
        for (int i = 0; i < 20; i++) {
			
        	 rabbitTemplate.convertAndSend("sltas.direct.exchange", "sltas.info", "message before", message -> {
                 
        		 
        		 /**
        			 * Durability：是否持久化，Durable是，Transient是否。如果不持久化，那么在服务器宕机或重启之后Queue就会丢失。
        			 * Auto delete：如果选择yes，当最后一个消费者不在监听Queue的时候，该Queue就会自动删除，一般选择false。
        			 * Arguments：AMQP协议留给AMQP实现者扩展使用的。
        			 * 		x-message-ttl：一个消息推送到队列中的存活时间。设置的值之后还没消费就会被删除。
        			 * 		x-expires：在自动删除该队列的时候，可以使用该队列的时间。
        			 * 		x-max-length：在队列头部删除元素之前，队列可以包含多少个（就绪）消息，如果再次向队列中发送消息，会删除最早的那条消息，用来控制队列中消息的数量。
        			 * 		x-max-length-bytes：在队列头部删除元素之前，队列的总消息体的大小，用来控制队列中消息的总大小。
        			 * 		x-dead-letter-exchange：当消息被拒绝或者消息过期，消息重新发送到的交换机（Exchange）的可选名称。
        			 * 		x-dead-letter-routing-key：当消息被拒绝或者消息过期，消息重新发送到的交换机绑定的Route key的名称，如果没有设置则使用之前的Route key。
        			 * 		x-max-priority：队列支持的最大优先级数，如果没有设置则不支持消息优先级
        			 * 		x-queue-mode：将队列设置为延迟模式，在磁盘上保留尽可能多的消息以减少RAM使用; 如果未设置，队列将保持在内存中的缓存，以尽可能快地传递消息。
        			 * 		x-queue-master-locator：将队列设置为主位置模式，确定在节点集群上声明队列主节点所在的规则。
        			 */
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
        
        
        TimeUnit.SECONDS.sleep(300);
        
        context.close();

    }

}
