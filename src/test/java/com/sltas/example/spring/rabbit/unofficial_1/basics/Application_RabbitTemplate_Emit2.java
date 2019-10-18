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
public class Application_RabbitTemplate_Emit2 {
	
	private static Logger logger = LoggerFactory.getLogger(Application_RabbitTemplate_Emit2.class);

	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application_RabbitTemplate_Emit2.class);

        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        logger.info("RabbitTemplate : {}",rabbitTemplate);
        
        /**
         * 使用convertAndSend方法，接收的参数是Object对象，其实是将接收的对象转换成Message对象,不指定exchange和routing key，那么就
         * 使用RabbitTemplate中设置的exchange和routing key
         */
        //rabbitTemplate.convertAndSend("this is my message");

        //指定exchange或者指定exchage和routing key
        //rabbitTemplate.convertAndSend("zhihao.error","this is my message order111");
        //rabbitTemplate.convertAndSend("","zhihao.user.queue","this is my message order222");

        //发送消息的后置处理器，MessagePostProcessor类的postProcessMessage方法得到的Message就是将参数Object内容转换成Message对象
        /*
        rabbitTemplate.convertAndSend("", "zhihao.user.queue", "this is my message processor", new MessagePostProcessor() {
            //在后置处理器上加上order和count属性
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                System.out.println("-------处理前message-------------");
                System.out.println(message);
                message.getMessageProperties().getHeaders().put("order",10);
                message.getMessageProperties().getHeaders().put("count",1);
                return message;
            }
        });
        */
        rabbitTemplate.convertAndSend("sltas.direct.exchange", "sltas.info", "message before", message -> {
            
          //使用lamdba的语法
            MessageProperties properties = new MessageProperties();
            properties.getHeaders().put("desc","消息发送");
            properties.getHeaders().put("type",10);

            Message messageafter = new Message("message after".getBytes(),properties);
            return messageafter;
            
        });
        
        
        TimeUnit.SECONDS.sleep(300);
        
        context.close();

    }

}
