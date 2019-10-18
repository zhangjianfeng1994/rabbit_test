package com.sltas.flow.rabbit.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.connection.CorrelationData.Confirm;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.sltas.flow.rabbit.consts.RabbitConsts;
import com.sltas.flow.rabbit.service.MessagePreprocessor;
import com.sltas.flow.rabbit.service.ProducerHandler;
import com.sltas.flow.redis.service.RedisService;

@Component("producerHandler")
public class ProducerHandlerImpl implements ProducerHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("redisService")
	private RedisService redisService;
	
	/**
	 * <p>
	 * Title: convertAndSend
	 * </p>
	 * <p>
	 * Description: 消息推送类
	 * 采用RabbitTemplate.convertAndSend 进行底层业务推送
	 * 
	 * 	发送端将出现三种异常情况（当前仅发现三种，后续还会继续增加）
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
	 * @param @param template
	 * @param @param message
	 * @param @return 
	 * @return Confirm
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年10月30日 下午5:39:32 
	 */
	@Override
	public Confirm convertAndSend(final RabbitTemplate rabbitTemplate, final MessagePreprocessor messagePreprocessor) {

		Assert.notNull(rabbitTemplate, "rabbitTemplate can not be null");
		Assert.notNull(messagePreprocessor, "messagePreprocessor can not be null");
		
		com.sltas.flow.rabbit.dto.MessageProperties preMessage = messagePreprocessor.createMessage();
		logger.info("[rabbit][producer] request -> MessageProperties : {} ", preMessage);
		
		/**
		 * 拼装 CorrelationData id
		 * 
		 * 例 ：RABBIT_CACHE@#R_102@#fe5c88e9-05f1-44bf-8de5-383ddfd24876@#true@#1541484660337
		 * 
		 * RABBIT_CACHE								//RABBIT_CACHE前缀
		 * R_102									//serviceId 业务类型ID
		 * fe5c88e9-05f1-44bf-8de5-383ddfd24876		//preMessage.getCorrelationDataId() 业务参数主键，保证业务异常的信息获取
		 * true										//是否开启redis缓存
		 * 1541484660337							//执行时间。避免业务特殊情况下，对执行时间进行超时处理
		 * 
		 */
		StringBuffer correlationDataId = new StringBuffer()
				.append(RabbitConsts.CD_PREFIX)					//RABBIT_CACHE前缀
				.append(RabbitConsts.CD_SEP)
				.append(preMessage.getServiceId())				//serviceId 业务类型ID
				.append(RabbitConsts.CD_SEP)
				.append(preMessage.getCorrelationDataId())		//preMessage.getCorrelationDataId() 业务参数主键，保证业务异常的信息获取
				.append(RabbitConsts.CD_SEP)
				.append(preMessage.getOpenCache())				//是否开启redis缓存
				.append(RabbitConsts.CD_SEP)
				.append(new Date().getTime());					//执行时间。避免业务特殊情况下，对执行时间进行超时处理
		
		CorrelationData correlationData = new CorrelationData(correlationDataId.toString());
		logger.info("[rabbit][producer] request -> {} ", correlationData);
		
		try {
			
			/**
			 * MessageProperties [headers={}, contentType=application/x-java-serialized-object, contentLength=631, deliveryMode=PERSISTENT, priority=0, deliveryTag=0]
			 */
			rabbitTemplate.convertAndSend(preMessage.getTargetExchange(), preMessage.getTargetRoutingKey(),
					preMessage.getBody(), message -> {
						// 使用lamdba的语法
						MessageProperties properties = message.getMessageProperties();
						/**
						 * spring_returned_message_correlation	spring默认参数 =》 CorrelationData.id 的数值
						 * spring_listener_return_correlation	待分析
						 */
//						properties.getHeaders().put(SystemUtil.QUEUE_DATA_SERVICE_ID, preMessage.getServiceId());
						properties.setPriority(preMessage.getPriority());
						properties.setExpiration(preMessage.getExpiration());
						/**
						 * 通过OpenCache 判定 是否 将Message message 对象保存至redis中
						 */
						if(preMessage.getOpenCache()){redisService.set(correlationData.getId(), message);}
						return message;
					} , correlationData);

		} catch (Exception e) {
			Confirm confirm = new Confirm(false, null);
			logger.error("[rabbit][producer] response -> {} ,error : {} ",confirm, e, e);
			//准备进行异常特殊处理
			return confirm;
		}
		
		/**
		 * 
		 * 注意点：此逻辑受控条件下
		 *  preMessage.getWaitConfirm() 是否开启超时等待结果
		 *	存在部分取舍，其中采取超时时间控制  10s 配置文件中统一配置，后续可以考虑非统一
		 *  如果超时 则会返回  Confirm confirm = new Confirm(true, SystemUtil.PRODUCER_RESPONSE_WAITING);
		 *  可以视为继续进行后续操作（因为队列为了保证速度 并没有开启TX模式，所以保证入队为流程的最后步骤,不会发生持久化的部分入库情况）
		 * 
		 * 不建议打开
		 * 本机环境下测试,压测情况下，估计比重会更大，有待测试
		 * Future 基础性质下， 例如，非get获取 6毫秒  ，get获取20毫秒，需根据不同业务情况对此进行开放。
		 * 谨开！！！！
		 */
		try {
			if(preMessage.getWaitResponse()){
				Confirm confirm = correlationData.getFuture().get(preMessage.getWaitTime(), TimeUnit.SECONDS);
				logger.info("[rabbit][producer]【wait】 response -> Confirm : {} ", confirm);
				return confirm;
			}
		} catch (Exception e) {
			Confirm confirm = new Confirm(true, RabbitConsts.PRODUCER_RESPONSE_WAITING);
			logger.error("[rabbit][producer]【wait】 response -> time out Confirm : {}, error : {} ",confirm, e, e);
			return confirm;
		}
		
		Confirm confirm = new Confirm(true, null);
		logger.info("[rabbit][producer] response -> Confirm : {} ", confirm);
		return confirm;
		
	}

}
