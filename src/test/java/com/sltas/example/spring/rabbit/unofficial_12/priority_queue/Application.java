package com.sltas.example.spring.rabbit.unofficial_12.priority_queue;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Application {

	 private static MessageProperties getmessageProperties(){
	        int priority = new Random().nextInt(5)+10;
	        System.out.println("====优先级==="+priority);
	        MessageProperties messageProperties = new MessageProperties();
	        messageProperties.setContentType("text");
	        
	        /**
	         * 总结：
	         * 
	         * 创建优先级队列，需要增加x-max-priority参数，指定一个数字。表示最大的优先级，建议优先级设置为1～10之间。
	         * 发送消息的时候，需要设置priority属性，最好不要超过上面指定的最大的优先级。
	         * 如果生产端发送很慢，消费者消息很快，则有可能不会严格的按照优先级来进行消费。
	         * 第一，如果发送的消息的优先级属性小于设置的队列属性x-max-priority值，则按优先级的高低进行消费，数字越高则优先级越高。
	         * 第二，如果发送的消息的优先级属性都大于设置的队列属性x-max-priority值，则设置的优先级失效，按照入队列的顺序进行消费。
	         * 第三，如果消费端一直进行监听，而发送端一条条的发送消息，优先级属性也会失效。
	         * 
	         */
	        
	        messageProperties.setPriority(priority);
	        return messageProperties;

	    }
	    public static void main(String[] args) throws Exception{
	        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
//	        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
//
//	        //一次性发送10条消息，优先级分别是1到10
//	        for (int i = 0; i < 10; i++) {
//	        	MessageProperties mp = getmessageProperties();
//	        	String bodys = ("hello world "+i+" "+mp.getPriority());
//	        	byte[] body = bodys.getBytes();
//	        	System.out.println("====优先级==="+bodys);
//	            rabbitTemplate.send("sltas.direct.exchange","sltas.info",new Message(body,mp));
//	        }

	        TimeUnit.SECONDS.sleep(30);

	        context.close();
	    }
	
}
