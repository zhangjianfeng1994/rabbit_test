package com.sltas.example.flow.rabbit.xml;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;

public class SimpleRabbitListenerErrorHandler implements RabbitListenerErrorHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * request- o.s.amqp.core.Message请求对象。
	 * source- o.s.messaging.Message<?>转换后。
	 * result - 方法结果。
	 */
	@Override
	public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message,
			ListenerExecutionFailedException exception) throws Exception {
		
		logger.info("Message : {}",amqpMessage);
		logger.info("Message : {}",message);
		logger.error("ListenerExecutionFailedException : {}",exception,exception);
		
        //抛出NullPointerException异常则重新入队列
//        throw new NullPointerException("消息消费失败");
        //当抛出的异常是AmqpRejectAndDontRequeueException异常的时候，则消息会被拒绝，且requeue=false
        //throw new AmqpRejectAndDontRequeueException("消息消费失败");
        //当抛出ImmediateAcknowledgeAmqpException异常，则消费者会被确认
//        throw new ImmediateAcknowledgeAmqpException("消息消费失败");
		
		RefundPojo pojo = new RefundPojo();
		pojo.setId("abc");
		pojo.setCreateTime(new Date());
		
		/**
		 * Object errorResult = this.errorHandler.handleError(amqpMessage, message, e);
					if (errorResult != null) {
						handleResult(new InvocationResult(errorResult, null, null), amqpMessage, channel, message);
					}
					else {
						logger.trace("Error handler returned no result");
					}
					根据此行代码 。
					1.如果返回实体则将进行sendTo的目标地址发送
					2.如果返回为null,仅进行 log trace 级别通知
		 */
		
		return null;
	}

}
