package com.sltas.example.spring.rabbit.unofficial_2.MessageListenerAdapter.emit;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sltas.example.spring.rabbit.unofficial_2.MessageListenerAdapter.recv.Order;

public class MessageHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    //通过设置setDefaultListenerMethod时候指定的方法名
    public void onMessage(byte[] message){
    	logger.info("---------onMessage-------byte[]------");
    	logger.info("{}",new String(message));
    }
    
    //通过设置setDefaultListenerMethod时候指定的方法名
    public void onMessage(String message){
    	logger.info("---------onMessage------String-------");
    	logger.info("{}",message);
    }
	
    public void onMessage(Map<?,?> message){
    	logger.info("---------onMessage------Map<?,?>-------");
    	logger.info("{}",message);
    }
    
    public void onMessage(List<?> message){
        logger.info("---------onMessage------List-------");
    	logger.info("{}",message);
    }

    public void onMessage(Order order){
    	logger.info("---------onMessage---Order-------------");
    	logger.info("{}",order);
    }
    
    public void onMessage(File message){
    	logger.info("-------onMessage---File message------------");
    	logger.info("{}",message.getName());
    }

}
