package com.sltas.example.spring.rabbit.unofficial_13.rpc.emit;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Application {
    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        //设置超时时间，单位是ms
        rabbitTemplate.setReplyTimeout(10000);

        String phone = "15634344321";
        String content ="周年庆，五折优惠";

        MessageProperties messageProperties = new MessageProperties();
        Message message = new Message((phone+":"+content).getBytes(),messageProperties);

        //rabbitTemplate.send("","sms",message);

        Message reply = rabbitTemplate.sendAndReceive("","sms",message,
                new CorrelationData(UUID.randomUUID().toString()));

        System.out.println(reply);
        System.out.println("message,body:"+new String(reply.getBody()));
        System.out.println("message,properties:"+reply.getMessageProperties());

        TimeUnit.SECONDS.sleep(30);
        context.close();
    }
    
    /**
     * 此时发现客户端接收的消息数据没有乱码，原因何在？我们总结一下就是服务器端处理器返回给客户端boolean类型，那么返回的消息数据就乱码，如果返回的是String类型，那么返回的消息数据就不会乱码。
     * 之前我们学习了org.springframework.amqp.support.converter.MessageConverter接口，当客户端向服务端发送消息的时候会进行消息类型转换，调用了fromMessage方法，而当服务器返回给客户端的时候会将服务端的对象转换成Message对象，很明显调用的是toMessage方法。
     * 我们知道org.springframework.amqp.support.converter.MessageConverter接口的默认实现是org.springframework.amqp.support.converter.SimpleMessageConverter，而toMessage方法的实现是在其继承的对象AbstractMessageConverter中，
     * 
     * 链接：https://www.jianshu.com/p/24b88ccfb019
     * 
     */
}
