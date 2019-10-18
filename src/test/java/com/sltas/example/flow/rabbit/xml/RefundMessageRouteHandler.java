package com.sltas.example.flow.rabbit.xml;
//package com.sltas.flow.rabbit.server;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.support.CorrelationData;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//
//import com.sltas.application.order.model.util.InnerCommonResponse;
//import com.sltas.application.order.model.util.OrderErrorEnum;
//import com.sltas.order.rabbit.server.RefundMessageRouteServer;
//import com.sltas.order.rabbit.server.util.MQSend;
//import com.sltas.order.rabbit.server.util.RabbitProcesses;
//
//@Service("refundMessageRouteServer")
//public class RefundMessageRouteHandler implements RefundMessageRouteServer {
//	
//	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
//	
//	/*************************退款执行*********OPEN***********************/
//	@Value("${rabbit.order.refund.execute.exchange}")
//	private String REFUND_EXECUTE_EXCHANGE;
//	@Value("${rabbit.order.refund.execute.routingKey}")
//	private String REFUND_EXECUTE_ROUTING_KEY;
//	/*************************退款执行*********END***********************/
//	
//	@Autowired
//	@Qualifier("rabbitTemplate")
//	private RabbitTemplate rabbitTemplate;
//	
//	/**
//	 * <p>
//	 * Title: refundExecute
//	 * </p>
//	 * <p>
//	 * Description: 退费执行（盒子退费+第三方快捷退款）
//	 * </p>
//	 * @param @param body
//	 * @param @param id
//	 * @param @param priority
//	 * @param @return 
//	 * @return InnerCommonResponse<?>
//	 * @throws
//	 * @author 周顺宇 
//	 * @date 2018年10月19日 上午9:46:15 
//	 */
//	@Override
//	public InnerCommonResponse<?> refundExecute(MQSend<String, ?> mqSend){
//		
//		try {
//			
//			Assert.notNull(mqSend, "mqSend can not be null");
//			Assert.notNull(mqSend.getCorrelationDataId(), "mqSend.correlationDataId can not be null");
//			Assert.notNull(mqSend.getData(), "mqSend.data can not be null");
//			
//			logger.info("CorrelationDataId : {}, mqSend : {}",mqSend.getCorrelationDataId(),mqSend);
//			
//			rabbitTemplate.convertAndSend(this.REFUND_EXECUTE_EXCHANGE, this.REFUND_EXECUTE_ROUTING_KEY,mqSend.getData(), message -> {
//				MessageProperties properties = message.getMessageProperties();
//				if(mqSend.getPriority() != null ){ properties.setPriority(mqSend.getPriority()); };
//				if(mqSend.getExpiration() != null ){ properties.setExpiration(mqSend.getExpiration()); };
//				properties.getHeaders().put(RabbitProcesses.BODY_CLAZZ, mqSend.getData().getClass().getName());
//				properties.getHeaders().put(RabbitProcesses.SERVER_ROUTER, RabbitProcesses.REFUND_EXECUTE);
//				return message;
//			}, new CorrelationData(mqSend.getCorrelationDataId()));
//		} catch (Exception e) {
//			
//			logger.error("CorrelationDataId : {}, error : {}",mqSend.getCorrelationDataId(),e,e);
//			/**
//			 * 需要对该条信息进行回退操作，因入队失败
//			 * 错误原因为，rabbitmq 数据源异常导致
//			 */
//			return InnerCommonResponse.serviceErrorReturn(OrderErrorEnum.ERROR_ORDER_22001);
//		}
//		return InnerCommonResponse.serviceErrorReturn(OrderErrorEnum.SUCCESS);
//		
//		
//	}
//	
//}
