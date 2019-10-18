package com.sltas.flow.api.server.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.sltas.flow.api.server.OrderRefundHandler;
import com.sltas.flow.dto.RefundOrder;
import com.sltas.flow.rabbit.consts.RabbitConsts;
import com.sltas.flow.service.OrderRefundService;
import com.sltas.flow.util.api.CommonResponseHeader;
import com.sltas.flow.util.server.ServiceSubtypeEnum;
import com.sltas.flow.util.server.SystemUtil;

@Controller("orderRefundHandler")
public class OrderRefundHandlerImpl implements OrderRefundHandler{

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("orderRefundService")
	private OrderRefundService orderRefundService;
	
	
	
	/**
	 * <p>
	 * Title: refundExecute
	 * </p>
	 * <p>
	 * Description: 受理退款 
	 * </p>
	 * @param @param refundOrder
	 * @param @return 
	 * @return CommonResponse<?>
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年10月30日 上午9:56:46 
	 */
	@Override
	public CommonResponseHeader<?> refundExecute(RefundOrder refundOrder) {
		
		MDC.put(SystemUtil.SID_LOG, ServiceSubtypeEnum.R_THIRD_PARTY_SEND.getServiceId());
		MDC.put(SystemUtil.OID_LOG, refundOrder.getTransNo());
		try {
			
			CommonResponseHeader<?> result = orderRefundService.refundExecute(refundOrder);
			logger.info("{}",result);
			return result;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			MDC.clear();
		}
		return null;
	}
	
	
	/**
	 * <p>
	 * Title: refundHandleMessage
	 * </p>
	 * <p>
	 * Description: 执行退款
	 * 
	 * 拼装 CorrelationData id
	 * 
	 * 例 ：RABBIT_CACHE@#R_102@#fe5c88e9-05f1-44bf-8de5-383ddfd24876@#true@#f5d4243a-d6ea-4294-96c1-4286dc03d3fd@#1541484660337
	 * 
	 * RABBIT_CACHE								//RABBIT_CACHE前缀
	 * R_102									//serviceId 业务类型ID
	 * fe5c88e9-05f1-44bf-8de5-383ddfd24876		//preMessage.getCorrelationDataId() 业务参数主键，保证业务异常的信息获取
	 * true										//是否开启redis缓存
	 * f5d4243a-d6ea-4294-96c1-4286dc03d3fd		//oid 操作系统的唯一流水信息，保证日志查找
	 * 1541484660337	
	 * 
	 * </p>
	 * @param @param message
	 * @param @param order 
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年11月6日 下午2:50:57 
	 */
	@RabbitListener(
			containerFactory="refundRabbitListenerContainerFactory",
			queues= "${rabbit.order.refund.execute.queue}")
//			queues={"${rabbit.order.refund.execute.queue}","${rabbit.order.refund.execute.alternate.queue}"})
//			errorHandler="customerListenerErrorHandler")
//			returnExceptions="false")
//	@SendTo("${rabbit.order.refund.execute.alternate.fanout.exchange}/")
	public void refundHandleMessage(Message message,@Payload RefundOrder order)  {
		
		
		 //抛出NullPointerException异常则重新入队列
 		 //throw new NullPointerException("消息消费失败");
 		 //当抛出的异常是AmqpRejectAndDontRequeueException异常的时候，则消息会被拒绝，且requeue=false
		 //throw new AmqpRejectAndDontRequeueException("消息消费失败");
 		 //当抛出ImmediateAcknowledgeAmqpException异常，则消费者会被确认
  		 //throw new ImmediateAcknowledgeAmqpException("消息消费失败");
		
		try {
			
			String correlationDataId = message.getMessageProperties().getHeaders().get("spring_returned_message_correlation").toString();
			
			String[] params = correlationDataId.split(RabbitConsts.CD_SEP);
			//保存日志信息
			MDC.put(SystemUtil.SID_LOG, params[1]);
			MDC.put(SystemUtil.OID_LOG, params[2]);
			
			logger.info("执行退款：{},Message : {} ",order,message);
			orderRefundService.refundHandleMessage(order);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			MDC.clear();
		}

	}
	
}
