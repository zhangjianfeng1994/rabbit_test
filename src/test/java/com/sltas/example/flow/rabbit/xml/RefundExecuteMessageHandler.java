package com.sltas.example.flow.rabbit.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Title: RefundExecuteMessageHandler.java
 * </p>
 * <p>
 * Description: 退费执行消息处理器（处理所有与退费有关系的队列信息）
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年10月18日 上午10:25:21  
 */
@Component
public class RefundExecuteMessageHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * 
	 * <p>
	 * Title: refundHandleMessage
	 * </p>
	 * <p>
	 * Description: 从2.0版开始，@RabbitListener注释有两个新属性：errorHandler和returnExceptions。
	 * 
	 * 如您所见，您可以访问从容器接收的原始消息，Message<?>消息转换器生成的spring-messaging 对象以及侦听器抛出的异常，包含在ListenerExecutionFailedException。错误处理程序可以返回一些将作为回复发送的结果，或者抛出将被抛出到容器的原始或新异常，或者返回给发送方，具体取决于returnExceptions设置。
	 * 
	 * 该returnExceptions属性，当“true”将导致异常返回给发件人。异常包含在一个RemoteInvocationResult对象中。在发送方，有一个可用的RemoteInvocationAwareMessageConverterAdapter，如果配置成RabbitTemplate，将重新抛出服务器端异常，包装在AmqpRemoteException。将通过合并服务器和客户端堆栈跟踪来合成服务器异常的堆栈跟踪。
	 * 
	 * [重要]	重要
	 * 这种机制通常只能使用默认的SimpleMessageConverter，它使用Java序列化; 例外通常不是“Jackson-friendly”，因此无法序列化为JSON。如果您使用的是JSON，请考虑在抛出异常时使用an errorHandler来返回其他一些对Jackson友好的Error对象。
	 * [重要]	重要
	 * 在2.1版中，此接口从包移动o.s.amqp.rabbit.listener到o.s.amqp.rabbit.listener.api。
	 * 
	 * 
	 * </p>
	 * @param @param message
	 * @param @param pojo 
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年10月25日 下午2:41:57
	 */

	@RabbitListener(
			containerFactory="refundRabbitListenerContainerFactory",
			queues= "${rabbit.order.refund.execute.queue}",
//			queues={"${rabbit.order.refund.execute.queue}","${rabbit.order.refund.execute.alternate.queue}"})
			errorHandler="simpleRabbitListenerErrorHandler")
//			returnExceptions="false")
//	@SendTo("${rabbit.order.refund.execute.alternate.exchange}/")
	public void refundHandleMessage(Message message,@Payload RefundPojo pojo)  {
		
		logger.info("消费者：{},Message : {} ",pojo,message);
		logger.info("消费者：{},Message : {} ",pojo,message);
		logger.info("消费者：{},Message : {} ",pojo,message);

//		try {
//			TimeUnit.SECONDS.sleep(1);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}
	
//	@RabbitListener(
//			containerFactory="refundRabbitListenerContainerFactory",
//			queues={"${rabbit.order.refund.execute.queue}","${rabbit.order.refund.execute.alternate.queue}"})
	
//	@RabbitListener(containerFactory="refundRabbitListenerContainerFactory",queues="${rabbit.order.refund.execute.queue}")

	
}
