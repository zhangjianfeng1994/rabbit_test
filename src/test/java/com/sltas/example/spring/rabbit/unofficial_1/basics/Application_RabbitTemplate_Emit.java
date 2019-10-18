package com.sltas.example.spring.rabbit.unofficial_1.basics;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
public class Application_RabbitTemplate_Emit {
	
	private static Logger logger = LoggerFactory.getLogger(Application_RabbitTemplate_Emit.class);

	
	public static void main(String[] args) throws Exception {
		
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application_RabbitTemplate_Emit.class);

        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        logger.info("RabbitTemplate : {}",rabbitTemplate);
        
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc","消息发送");
        messageProperties.getHeaders().put("type",10);

        Message message = new Message("hello".getBytes(),messageProperties);
        
        /**
         * 调用rabbitTemplate的send方法发送消息，如果没有指定exchange，Routing，则使用声明Exchange指定的
         * exchange，Routing
         * 如果RabbitTemplate没有设置，则默认的exchange 是DEFAULT_EXCHANGE为""，
         * 默认的routkey是DEFAULT_ROUTING_KEY为""
         */
        //1.
        //rabbitTemplate.send(message);

        //2.指定Routingkey,而exchange是Rabbitmq默认指定的
        //rabbitTemplate.send("zhihao.error",message);

        //3.即指定exchange，又指定了routing_key
        //rabbitTemplate.send("zhihao.login","ulogin",message);

        /**
         * 4.使用默认的defaultExchange进行投递消息，route key就是队列名，指定correlation_id属性，correlation_id属性是rabbitmq 进行异步rpc进行标识每次请求的唯一
         * id,下面会讲到
         */
        rabbitTemplate.send("sltas.direct.exchange","sltas.info",message,new CorrelationData("spring.amqp"));
        
        TimeUnit.SECONDS.sleep(300);
        
        context.close();

    }

}
