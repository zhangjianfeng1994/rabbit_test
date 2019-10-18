package com.sltas.flow.rabbit.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.messaging.handler.annotation.SendTo;

import com.sltas.flow.dto.RefundOrder;
import com.sltas.flow.util.server.SystemUtil;

/**
 * <p>
 * Title: CustomerListenerErrorHandler.java
 * </p>
 * <p>
 * Description: 消费者错误处理器
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年11月6日 上午11:40:52  
 */
public class CustomerListenerErrorHandler implements RabbitListenerErrorHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * <p>
	 * Title: handleError
	 * </p>
	 * <p>
	 * Description: 异常消息处理
	 * 
	 * 需要注意：@SendTo("${rabbit.order.refund.execute.alternate.fanout.exchange}/")
	 * 其中SendTo当消费者存在返回值，会将此值返回给其他交换机，形成异步应答队列的效果。
	 * 当返回值为void则此值不存在效果，但是当RabbitListenerErrorHandler 指向处理后，则会获取SendTo的值，将异常情况发送至error的特殊处理队列，进行处理。
	 * 
	 * request- o.s.amqp.core.Message请求对象。
	 * source- o.s.messaging.Message<?>转换后。
	 * result - 方法结果。
	 * 
	 * </p>
	 * @param  
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年11月6日 上午11:40:10 
	 */
	@Override
	public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message,
			ListenerExecutionFailedException exception) throws Exception {
		
		logger.error("Message type : {} content : {}",amqpMessage.getClass(),amqpMessage);
		logger.error("Message type : {} content : {}",message.getClass(),message);
		logger.error("ListenerExecutionFailedException : {}",exception,exception);
		logger.error("Message body : {}",amqpMessage.getBody());
		
		return null;
		
		/**
		 * 
		 * 抛出NullPointerException异常则重新入队列
 		 * throw new NullPointerException("消息消费失败");
 		 * 当抛出的异常是AmqpRejectAndDontRequeueException异常的时候，则消息会被拒绝，且requeue=false
		 * throw new AmqpRejectAndDontRequeueException("消息消费失败");
 		 * 当抛出ImmediateAcknowledgeAmqpException异常，则消费者会被确认
  		 * throw new ImmediateAcknowledgeAmqpException("消息消费失败");
		 * 
		 * 
		 * Object errorResult = this.errorHandler.handleError(amqpMessage, message, e);
					if (errorResult != null) {
						handleResult(new InvocationResult(errorResult, null, null), amqpMessage, channel, message);
					}
					else {
						logger.trace("Error handler returned no result");
					}
					根据此行代码 。
					1.如果返回实体则将进行sendTo的目标地址发送 @SendTo("${rabbit.order.refund.execute.alternate.fanout.exchange}/")
					2.如果返回为null,仅进行 log trace 级别通知
		 */
	}

}
