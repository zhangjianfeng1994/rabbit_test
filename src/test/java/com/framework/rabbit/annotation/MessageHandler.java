package com.framework.rabbit.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

import com.rabbitmq.client.Channel;

public class MessageHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	//没有设置默认的处理方法的时候，方法名是handleMessage
    public void handleMessage(byte[] message){
    	logger.info("---------handleMessage-----byte[]--------");
    	logger.info(new String(message));
    }
    
    public void handleMessage(String message){
    	logger.info("---------handleMessage----String---------");
    	logger.info(message);
    }
    
    //通过设置setDefaultListenerMethod时候指定的方法名
    public void onMessage(byte[] message){
    	logger.info("---------onMessage-------byte[]------");
    	logger.info(new String(message));
    }
    
    //通过设置setDefaultListenerMethod时候指定的方法名
    public void onMessage(String message){
    	logger.info("---------onMessage------String-------");
    	logger.info(message);
    }

}
