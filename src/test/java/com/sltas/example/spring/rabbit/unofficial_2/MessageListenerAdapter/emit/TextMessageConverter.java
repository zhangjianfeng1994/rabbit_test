package com.sltas.example.spring.rabbit.unofficial_2.MessageListenerAdapter.emit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class TextMessageConverter implements MessageConverter {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Override
	public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
		logger.info("=======toMessage=========");
        return new Message(object.toString().getBytes(),messageProperties);
	}

	//消息类型转换器中fromMessage方法返回的类型就是消费端处理器接收的类型
	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		logger.info("=======fromMessage=========");
        return message.getBody();
	}

}
