package com.sltas.example.spring.rabbit.unofficial_4.RabbitListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues ="sltas.info",containerFactory="rabbitListenerContainerFactory2")
public class MessageHandler2 {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@RabbitHandler
    public void handleMessage(byte[] message){
        System.out.println("====消费消息handleMessage");
        System.out.println(new String(message));
    }

    @RabbitHandler
    public void handleMessage2(String message){
        System.out.println("====消费消息===handleMessage2");
        System.out.println(message);
    }
    
    @RabbitHandler
    public void handleMessage3(User user){
        System.out.println("====消费消息===handleMessage3");
        System.out.println(user);
    }
	
}
