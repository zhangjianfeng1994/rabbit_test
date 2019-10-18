package com.sltas.flow.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sltas.flow.dto.RefundOrder;
import com.sltas.flow.dto.ResultPush;
import com.sltas.flow.rabbit.config.RabbitConfigure;
import com.sltas.flow.rabbit.dto.MessagePropertiesBuilder;
import com.sltas.flow.rabbit.dto.NackCause;
import com.sltas.flow.rabbit.service.ProducerHandler;
import com.sltas.flow.service.OrderRefundService;
import com.sltas.flow.util.api.CommonResponseHeader;
import com.sltas.flow.util.api.ResponseResult;
import com.sltas.flow.util.server.ServiceSubtypeEnum;

@Service("orderRefundService")
public class OrderRefundServiceImpl implements OrderRefundService{

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("rabbitTemplate")
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	@Qualifier("producerHandler")
	private ProducerHandler producerHandler;
	
	/**
	 * <p>
	 * Title: refundHandleMessage
	 * </p>
	 * <p>
	 * Description: 执行退款 
	 * </p>
	 * @param @param message
	 * @param @param order 
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年11月6日 下午2:58:40 
	 */
	@Override
	public void refundHandleMessage(RefundOrder order)  {
		
		
		ResultPush rp = new ResultPush();
		rp.setId(UUID.randomUUID().toString());
		rp.setTransNo(order.getTransNo());
		rp.setMsg("退费成功");
		
		logger.info("执行退款ing ==== ：{} 准备返回报告 {}",order,rp);
		
		producerHandler.convertAndSend(rabbitTemplate, ()->{
			return MessagePropertiesBuilder.newInstance()
				.setServiceId(ServiceSubtypeEnum.S_REFUND.getServiceId())
				.setCorrelationDataId(rp.getId())
				.setTargetExchange(RabbitConfigure.response_push_direct_exchange)
				.setTargetRoutingKey(RabbitConfigure.response_push_ttl_routing_key)
				.setBody(rp)
				.setOpenCache(true)
				.setExpiration("60000")
				.build();
		});

	}
	
	
	/**
	 * <p>
	 * Title: refundExecute
	 * </p>
	 * <p>
	 * Description: 发起退款 
	 * </p>
	 * @param @param refundOrder
	 * @param @return 
	 * @return CommonResponse<?>
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年10月30日 上午9:56:46 
	 */
	@Override
	public CommonResponseHeader<?> refundExecute(final RefundOrder refundOrder) {
		
		producerHandler.convertAndSend(rabbitTemplate, ()->{
			return MessagePropertiesBuilder.newInstance()
				.setServiceId(ServiceSubtypeEnum.R_THIRD_PARTY_SEND.getServiceId())
				.setCorrelationDataId(refundOrder.getTransNo())
				.setTargetExchange(RabbitConfigure.refund_execute_direct_exchange)
				.setTargetRoutingKey(RabbitConfigure.refund_execute_routing_key)
				.setBody(refundOrder)
				.setOpenCache(true)
//				.setExpiration("40000")
//				.setPriority(0)
//				.setWaitConfirm(true)
//				.setWaitTime(20)
				.build();
		});
	
    	return CommonResponseHeader.serviceReturn(ResponseResult.SUCCESS, null);
    	
	}
	
	@Override
	public Boolean refundExecuteNack(NackCause nc){
		
		logger.info("信息异常了 ！！！！===== {}",nc);
		logger.info("信息异常了 ！！！！===== {}",nc);
		logger.info("信息异常了 ！！！！===== {}",nc);
		
		return true;
		
	}
	
}
