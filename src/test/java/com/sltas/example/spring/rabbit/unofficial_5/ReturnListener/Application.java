package com.sltas.example.spring.rabbit.unofficial_5.ReturnListener;

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

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc","消息发送");
        messageProperties.getHeaders().put("token","234sdfsdf3r342dsfd1232");

        //路由不到指定的队列
        rabbitTemplate.convertAndSend("sltas.direct.exchange","sltas.info.routingKey1","hello welcome");

        TimeUnit.SECONDS.sleep(1000);

        context.close();
    }
}
