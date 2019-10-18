package com.sltas.example.spring.rabbit.unofficial_8.Alternate_Exchange.C;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Application {

	public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        System.out.println(rabbitTemplate);

        byte[] body = "head.info.exchange!@".getBytes();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("json");

        Message message = new Message(body,messageProperties);

        //正确路由到header.info队列
        rabbitTemplate.send("head.info.exchange","head.error.a.b",message);

        TimeUnit.SECONDS.sleep(3000);

        context.close();
    }
	
	/**
	 * 总结
	 * 
	 * 建议Alternate Exchange的类型是fanout，防止出现路由失败。
	 * fanout exchange一般不需要指定Alternate Exchange属性。
	 * 如果一个Exchange指定了Alternate Exchange，那就意味着，当Exchange和Alternate Exchange都无法路由的时候，才会触发return method。
	 */
	
}

