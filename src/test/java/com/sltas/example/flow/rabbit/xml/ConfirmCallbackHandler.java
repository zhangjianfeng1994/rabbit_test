package com.sltas.example.flow.rabbit.xml;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.connection.CorrelationData.Confirm;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;

public class ConfirmCallbackHandler implements ConfirmCallback {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	/**
	 * ConfirmCallback支持一个RabbitTemplate。
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		
		logger.info("rabbitTemplate.setConfirmCallback() confirm() correlationData[message] ： {}",correlationData.getReturnedMessage());
		
		try {
			logger.info("rabbitTemplate.setConfirmCallback() confirm() correlationData {}",correlationData.getFuture().get(10, TimeUnit.SECONDS).isAck());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        if(ack){
        	logger.info("确认者===消息id为: {} 的消息，已经被ack成功",correlationData);
        }else{
        	logger.info("确认者===消息id为: {} 的消息，消息nack，失败原因是：{}",correlationData,cause);
        	/**
    		 * 需要对该条信息进行回退操作，因入队失败
    		 * 错误原因为，rabbitmq 通过 exchange 无法被检索到，消息确认失败。
    		 */
        }
        
        try {
			Confirm confirm = correlationData.getFuture().get(10, TimeUnit.SECONDS);
			logger.info("{}",confirm);
		} catch (Exception e) {
			e.printStackTrace();
		}

//        throw new NullPointerException("消息消费失败");
        
//        logger.info("{}",correlationData);
        
	}

}
