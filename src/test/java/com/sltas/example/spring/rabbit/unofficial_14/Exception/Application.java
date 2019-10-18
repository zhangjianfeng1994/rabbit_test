package com.sltas.example.spring.rabbit.unofficial_14.Exception;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        //使得客户端第一次连接rabbitmq
        context.getBean(RabbitAdmin.class).getQueueProperties("**");
        context.close();
    }
}
