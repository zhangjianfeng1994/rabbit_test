package com.sltas.example.spring.rabbit.unofficial_2.MessageListenerAdapter.emit;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class JPGMessageConverter implements MessageConverter{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		logger.info("====JPGMessageConverter====");
        byte[] body = message.getBody();
        String fileName = UUID.randomUUID().toString();
        String path = "D:/"+fileName+".jpg";
        File file = new File(path);
        try{
            Files.copy(new ByteArrayInputStream(body),file.toPath());
        }catch (IOException e){
            e.printStackTrace();
        }
        return file;

	}

}
