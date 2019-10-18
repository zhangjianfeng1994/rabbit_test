package com.sltas.example.spring.rabbit.unofficial_10.Queue_Length_Limit;

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

        byte[] body = "hello".getBytes();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("json");

        Message message = new Message(body,messageProperties);

        rabbitTemplate.send("","weixin",message);

        TimeUnit.SECONDS.sleep(30);

        context.close();
    }
}
