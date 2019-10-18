package com.sltas.example.spring.rabbit.unofficial_11.Dead_Letter_Exchange.recv;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Application {
    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        System.out.println(rabbitTemplate);

        TimeUnit.SECONDS.sleep(30);

        context.close();
    }
}
