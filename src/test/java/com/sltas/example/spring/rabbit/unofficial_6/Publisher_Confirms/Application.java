package com.sltas.example.spring.rabbit.unofficial_6.Publisher_Confirms;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Application {

    public static Order createOrder(){
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setCreateTime(LocalDateTime.now().toString());
        order.setPrice(100L);
        return order;
    }

    public static void saveOrder(Order order){
        //入库操作
        System.out.println("入库操作");
    }

    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        Order order  = createOrder();

        saveOrder(order);

//        ObjectMapper objectMapper = new ObjectMapper();
//        byte[] body = objectMapper.writeValueAsBytes(order);
//
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setContentType("json");
//
//        Message message = new Message(body,messageProperties);
//
//        System.out.println("id: "+order.getOrderId());
//
//        //指定correlationData的值
//        rabbitTemplate.send("sltas.direct.exchange1","sltas.info",message,new CorrelationData(order.getOrderId().toString()));

        TimeUnit.SECONDS.sleep(10);

        context.close();
    }
}
