package com.sltas.flow.rabbit.service.impl;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.sltas.flow.application.SpringContextUtil;
import com.sltas.flow.rabbit.consts.RabbitConsts;
import com.sltas.flow.rabbit.dto.NackCause;
import com.sltas.flow.rabbit.dto.NackCause.ReturnNack;
import com.sltas.flow.rabbit.service.ProducerAckHandler;
import com.sltas.flow.redis.service.RedisService;
import com.sltas.flow.util.server.ProducerNackConfiguration;
import com.sltas.flow.util.server.ServiceSubtypeCache;
import com.sltas.flow.util.server.ServiceSubtypeEnum;
import com.sltas.flow.util.server.SystemUtil;

/**
 * <p>
 * Title: ProducerAckHandlerImpl.java
 * </p>
 * <p>
 * Description: 生产者确认处理器
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年11月2日 上午10:00:34  
 */
@Component("producerAckHandler")
public class ProducerAckHandlerImpl implements ProducerAckHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("redisService")
	private RedisService redisService;
	
	@Autowired
	@Qualifier("simpleMessageConverter")
	private MessageConverter simpleMessageConverter;
	
	/**
	 * <p>
	 * Title: ack
	 * </p>
	 * <p>
	 * Description: 消息确认应答 ACK
	 * 
	 * 解析CorrelationData id
	 * 并根据信息规则进行解析，是否存在缓存信息，并解锁
	 * 
	 * </p>
	 * @param  
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年11月1日 下午6:39:11 
	 */
	@Override
	public void ack(CorrelationData correlationData){
		
		try {
			String[] params = correlationData.getId().split(RabbitConsts.CD_SEP);
			//保存日志信息
			MDC.put(SystemUtil.SID_LOG, params[1]);
			MDC.put(SystemUtil.OID_LOG, params[2]);
			
			logger.info("[Rabbit-Producer-Confirm][ACK] {} , openCache : {}",correlationData,params[3]);
			
			/**
			 * 判定是否开启缓存
			 * true 进行缓存清理，因生产者发送成功
			 */
			if(Boolean.valueOf(params[3])){
				redisService.delete(correlationData.getId());
			}
	    	logger.info("[Rabbit-Producer-Confirm][ACK] {} 已经被ack成功",correlationData);
	    	
		} catch (Exception e) {
			logger.error("[Rabbit-Producer-Confirm][ACK][error] : {}",e,e);
		}finally{
			MDC.clear();
		}
		
	}
	
	@Override
	public void nackConfirm(CorrelationData correlationData,String cause){
		
		try {
			String[] params = correlationData.getId().split(RabbitConsts.CD_SEP);
			//保存日志信息
			MDC.put(SystemUtil.SID_LOG, params[1]);
			MDC.put(SystemUtil.OID_LOG, params[2]);
			
			logger.info("[Rabbit-Producer-Confirm][NACK] {} , openCache : {} ",correlationData,params[3]);
			
			NackCause nack = new NackCause();
			nack.setNackType(NackCause.NACK_CONFIRM);
			nack.setCorrelationDataId(params[2]);
			nack.setServiceId(params[1]);
			nack.setCause(cause);
			
			/**
			 * 判定是否开启缓存
			 * true 进行缓存清理，因生产者发送成功
			 * 因如果没有开启缓存 CorrelationData 中
			 */
			Message message = null;
			if(Boolean.valueOf(params[3])){
				message = redisService.get(correlationData.getId(), Message.class);
				redisService.delete(correlationData.getId());
				logger.info("[Rabbit-Producer-Confirm][NACK] Message : {}",message);
//				if(message == null){
//		    		//此处可以之后添加告警信息,或者异常方案
//		    	}
				//类型转换稍后需要仔细看看
				nack.setBody(simpleMessageConverter.fromMessage(message));
			}
			//调用方法路由
			this.serviceRouter("[Rabbit-Producer-Confirm][NACK] ",nack);
	    	logger.info("[Rabbit-Producer-Confirm][NACK] {} 消息nack，失败原因是：{}",correlationData,cause);
		} catch (Exception e) {
			logger.error("[Rabbit-Producer-Confirm][NACK][error] : {}",e,e);
		}finally{
			MDC.clear();
		}
		
	}

	@Override
	public void nackReturn(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		
		try {
			
			String correlationDataId = message.getMessageProperties().getHeaders().get("spring_returned_message_correlation").toString();
			
			String[] params = correlationDataId.split(RabbitConsts.CD_SEP);
			//保存日志信息
			MDC.put(SystemUtil.SID_LOG, params[1]);
			MDC.put(SystemUtil.OID_LOG, params[2]);
			
			logger.info("[Rabbit-Producer-Return] {} , openCache : {} ",correlationDataId,params[3]);
			
			NackCause nack = new NackCause();
			nack.setNackType(NackCause.NACK_RETURN);
			nack.setCorrelationDataId(params[2]);
			nack.setServiceId(params[1]);
			//类型转换稍后需要仔细看看
			nack.setBody(simpleMessageConverter.fromMessage(message));
			
			ReturnNack returnNack = new ReturnNack();
			returnNack.setExchange(exchange);
			returnNack.setRoutingKey(routingKey);
			returnNack.setReplyCode(replyCode);
			returnNack.setReplyText(replyText);
			nack.setCause(returnNack.toString());
			nack.setReturnNack(returnNack);
			//调用方法路由
			this.serviceRouter("[Rabbit-Producer-Return] ",nack);
	    	logger.info("[Rabbit-Producer-Return] {} 消息nack，失败原因是：{}",correlationDataId,nack.getCause());
		} catch (Exception e) {
			logger.error("[Rabbit-Producer-Return] [error] : {}",e,e);
		}finally{
			MDC.clear();
		}
		
	}
	
	
	/**
	 * <p>
	 * Title: serviceRouter
	 * </p>
	 * <p>
	 * Description: service方法路由
	 * </p>
	 * @param @param nack 
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年11月5日 下午3:12:17 
	 */
	public void serviceRouter(String logMark,NackCause nack) {
		
		ServiceSubtypeEnum subtype = ServiceSubtypeCache.subtypeCache.get(nack.getServiceId());
		Assert.notNull(subtype, "ServiceSubtype can not be null");
		
		ProducerNackConfiguration pnc = subtype.getProducerNackConfiguration();
		
		logger.info("{} 检测service配置信息：{}",logMark,pnc);
		/**
		 * ProducerNackConfiguration 如果没有设置对应的service接收类，则进行消息丢弃
		 */
		if(pnc != null){
			Object service = SpringContextUtil.getBean(pnc.getNackServiceName());
			//获取反射方法对象
			Method method = ReflectionUtils.findMethod(service.getClass(), pnc.getNackServiceMethod(), pnc.getParamTypes());
			Object obj = ReflectionUtils.invokeMethod(method, service, nack);
			logger.info("{} 调用service result ： {}",logMark,obj);
		}
		
//		Method method = ReflectionUtils.findRequiredMethod(service.getClass(), subtype.getNackServiceMethod(), parameterTypes)
		
	}
	
}
