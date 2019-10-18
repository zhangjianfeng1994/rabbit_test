package com.sltas.example.spring.rabbit.unofficial_8.Alternate_Exchange.A;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.TimeUnit;

@ComponentScan
public class Application {
    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        System.out.println(rabbitTemplate);

        byte[] body = "sltas.hehe.exchange~233哈哈".getBytes();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("json");

        Message message = new Message(body,messageProperties);

        rabbitTemplate.send("sltas.hehe.exchange","sltas.a.b",message);

        TimeUnit.SECONDS.sleep(3);

        context.close();
    }
}
