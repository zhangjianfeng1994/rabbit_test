package com.sltas.example.flow.rabbit.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;


public class ReturnCallbackHandler implements ReturnCallback {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * ReturnCallback每个只支持一个RabbitTemplate。另请参阅“回复超时”一节。
	 */
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		
		logger.info("rabbitTemplate.setReturnCallback() returnedMessage() ");
		logger.info("message: {}",message);
    	logger.info("replyCode: {}",replyCode);
    	logger.info("replyText: {}",replyText);
    	logger.info("exchange: {}",exchange);
    	logger.info("routingKey: {}",routingKey);
    
    	/**
		 * 需要对该条信息进行回退操作，因入队失败
		 * 错误原因为，rabbitmq 通过 exchange + routingKey 无法检索到 queue报出
		 * 如果启用了 备用交换机，备用换机也检索不到queue的情况，也会报出
		 */
    	
    	
    	/**
    	 * 在该方法中进行异常抛出，会直接到达 publisher confirms 发送确认，
    	 * 并且返回消息为失败
    	 * clean channel shutdown; protocol method: #method<channel.close>(reply-code=200, reply-text=Closed due to exception from ReturnListener.handleReturn, class-id=0, method-id=0)
    	 * 但是并不会影响本类的下次接受,但是也不会报出到调用方法的try catch
    	 * throw new NullPointerException("消息消费失败");
    	 */
    	
//    	RefundPojo pojo =  (RefundPojo)SerializationUtils.deserialize(message.getBody());
//    	logger.info("============={}",pojo);
    	
    	
	}

}
