package com.sltas.example.spring.rabbit.unofficial_1.basics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	//没有设置默认的处理方法的时候，方法名是handleMessage
    public void handleMessage(byte[] message){
    	logger.info("---------handleMessage-------------");
    	logger.info(new String(message));
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

    //以下指定不同的队列不同的处理方法名
    public void onorder(byte[] message){
    	logger.info("---------onorder-------------");
    	logger.info(new String(message));
    }

    public void onpay(byte[] message){
    	logger.info("---------onpay-------------");
    	logger.info(new String(message));
    }

    public void oninfo(byte[] message){
    	logger.info("---------oninfo-------------");
    	logger.info(new String(message));
    }
	
}
