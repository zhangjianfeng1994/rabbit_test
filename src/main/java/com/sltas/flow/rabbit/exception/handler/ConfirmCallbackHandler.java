package com.sltas.flow.rabbit.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.sltas.flow.rabbit.service.ProducerAckHandler;

/**
 * <p>
 * Title: ConfirmCallbackHandler.java
 * </p>
 * <p>
 * Description: 《发送确认》
 * 
 * 发送端将出现三种异常情况（当前仅发现三种，后续还会继续增加）
 * 		 
 * 1.消息无法到达 （ReturnListener）,其中exchange找不到将触发Channel shutdown, routingKey找不到才会触发下面的方法,
 * 将在exchange通过routingKey不到指定的queue时触发rabbitTemplate.setReturnCallback()的方法
 * PS:因mandatory的特性，此错误必须捕获。否则将会对消息进行丢弃，无从查找,
 *	而且发送确认（publisher confirms）返回的应答结果是true,所以针对发送异常例如：routingKey找不到指定queue务必捕获这个异常,否则程序主体不会try catch捕获不到异常,一定注意！
 * 
 * 
 * 2.发送确认（publisher confirms）,其中exchange找不到将触发Channel shutdown, 
 * 将在exchange找不到时报出  reply-code=404, reply-text=NOT_FOUND - no exchange,
 * 但是会通过rabbitTemplate.setConfirmCallback()报出错误
 * PS:发送确认不进行捕获的同时，会在log4j中打印 Channel shutdown: channel error ,下面的error日志，但是仍然建议进行捕获,而且仍然不会触发try catch。
 * 	丢失数据，要比重复提交同样的消息更加可怕，保持程序的幂等性！
 * CachingConnectionFactory.java log(1349) - Channel shutdown: channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'sltas.direct.exchange1' in vhost '/', class-id=60, method-id=40)
 * PS2:闪断情况会导致 publisher confirms 返回 false
 * 	
 * 3.数据源异常
 * 例如：rabbitTemplate.convertAndSend 发送数据时，数据源异常，会直接触发try catch，此时的异常捕获才起到作用。
 * 	
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年11月2日 上午9:39:54  
 */
public class ConfirmCallbackHandler implements ConfirmCallback {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("producerAckHandler")
	private ProducerAckHandler producerAckHandler;
	
	@Autowired
	@Qualifier("rabbitConfirmExecutor")
	private ThreadPoolTaskExecutor rabbitConfirmExecutor;
	
	/**
	 * ConfirmCallback支持一个RabbitTemplate。
	 * 返回报告的处理速度会影响到发送者，建议采用多线程处理
	 * 原因待查找，后续会进行源码分析
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		
		//获取并解析参数
		logger.info("[Rabbit-Producer-Confirm][ACK] : {} , CorrelationData : {}, Cause : {}",ack,correlationData,cause);
		try {
//			TimeUnit.SECONDS.sleep(1000l);
			if(ack){
				rabbitConfirmExecutor.execute( ()-> {producerAckHandler.ack(correlationData);});
	        }else{
	        	/**
				 * 2.发送确认（publisher confirms）,其中exchange找不到将触发Channel shutdown, 
				 * 将在exchange找不到时报出  reply-code=404, reply-text=NOT_FOUND - no exchange,
				 * 但是会通过rabbitTemplate.setConfirmCallback()报出错误
				 * PS:发送确认不进行捕获的同时，会在log4j中打印 Channel shutdown: channel error ,下面的error日志，但是仍然建议进行捕获,而且仍然不会触发try catch。
				 * 	丢失数据，要比重复提交同样的消息更加可怕，保持程序的幂等性！
				 * CachingConnectionFactory.java log(1349) - Channel shutdown: channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'sltas.direct.exchange1' in vhost '/', class-id=60, method-id=40)
				 * PS2:闪断情况会导致 publisher confirms 返回 false
	        	 */
	        	rabbitConfirmExecutor.execute( ()-> {producerAckHandler.nackConfirm(correlationData,cause);});
	        }
		} catch (Exception e) {
			logger.error("[Rabbit-Producer-Confirm][error] CorrelationData : {}, error : {}",correlationData,e,e);
		}
        
	}
}
