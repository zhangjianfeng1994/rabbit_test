package com.sltas.flow.rabbit.exception.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.sltas.flow.rabbit.service.ProducerAckHandler;
import com.sltas.flow.util.server.SystemUtil;

/**
 * <p>
 * Title: ReturnCallbackHandler.java
 * </p>
 * <p>
 * Description: 《消息无法到达》 
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
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年11月2日 上午9:40:23  
 */
public class ReturnCallbackHandler implements ReturnCallback {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	// 日志格式初始化
	protected static String context_format = null;
	
	static {
		context_format = SystemUtil.SEP + "****************************************************" + SystemUtil.SEP;
		context_format += "[Rabbit-Producer-Return] Message : {}" + SystemUtil.SEP;
		context_format += "* replyCode : {} " + SystemUtil.SEP;
		context_format += "* replyText : {} " + SystemUtil.SEP;
		context_format += "* exchange : {} " + SystemUtil.SEP;
		context_format += "* routingKey : {} " + SystemUtil.SEP;
		context_format += "****************************************************" + SystemUtil.SEP;

	}

	@Autowired
	@Qualifier("producerAckHandler")
	private ProducerAckHandler producerAckHandler;
	
	@Autowired
	@Qualifier("rabbitConfirmExecutor")
	private ThreadPoolTaskExecutor rabbitConfirmExecutor;

	/**
	 * ReturnCallback每个只支持一个RabbitTemplate。另请参阅“回复超时”一节。
	 *  
	 * 其中 ReturnCallback 调用后 会将结果返回给 ConfirmCallback ,如果发生异常 confirm 方法会 ack = false
	 * 则会产生错误结果的二次触发，建议独立捕获returnedMessage中的所有异常，并进行后续的补救处理
	 * 
	 * 返回报告的处理速度会影响到发送者，建议采用多线程处理
	 * 原因待查找，后续会进行源码分析 
	 */
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		
		logger.info(context_format,message,replyCode,replyText,exchange,routingKey);
		try {
			
//			TimeUnit.SECONDS.sleep(10);
			
        	/**
			 * 1.消息无法到达 （ReturnListener）,其中exchange找不到将触发Channel shutdown, routingKey找不到才会触发下面的方法,
			 * 将在exchange通过routingKey不到指定的queue时触发rabbitTemplate.setReturnCallback()的方法
			 * PS:因mandatory的特性，此错误必须捕获。否则将会对消息进行丢弃，无从查找,
			 *	而且发送确认（publisher confirms）返回的应答结果是true,所以针对发送异常例如：routingKey找不到指定queue务必捕获这个异常,否则程序主体不会try catch捕获不到异常,一定注意！
        	 */
        	rabbitConfirmExecutor.execute( ()-> {producerAckHandler.nackReturn(message,replyCode,replyText,exchange,routingKey);});
        	
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
        	
		} catch (Exception e) {
			logger.error("[Rabbit-Producer-Return][error] Message : {}, error : {}",message,e,e);
		}
    	
	}
	
}
