package com.sltas.flow.rabbit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * <p>
 * Title: RabbitConfigure.java
 * </p>
 * <p>
 * Description: rabbit 配置信息类
 * 
 * 对于static变量
 * 
 * 只能在setXXX方法上加注解，另外class需要加 @Component等注解，这样spring才能扫描到
 * 
 * <context:component-scan base-package="com.sltas.flow" />
 * 
 * spring不支持给静态变量的原因
 * 
 * Spring的@Value依赖注入是依赖set方法
 * 
 * set方法是普通的对象方法
 * 
 * static变量是类的属性，static没有set方法。
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年10月30日 上午11:20:17  
 */
@Component
public class RabbitConfigure {
	
	/*************************退款执行*********OPEN***********************/
	
	public static String refund_execute_direct_exchange;
	
	public static String refund_execute_routing_key;
	
	@Value("${rabbit.order.refund.execute.direct.exchange}")
	public void setRefundExecuteDirectExchange(String refund_execute_direct_exchange) {
		Assert.notNull(refund_execute_direct_exchange, "refund_execute_direct_exchange can not be null");
		RabbitConfigure.refund_execute_direct_exchange = refund_execute_direct_exchange;
	}
	
	@Value("${rabbit.order.refund.execute.routingKey}")
	public void setRefundExecuteRoutingKey(String refund_execute_routing_key) {
		Assert.notNull(refund_execute_routing_key, "refund_execute_routing_key can not be null");
		RabbitConfigure.refund_execute_routing_key = refund_execute_routing_key;
	}
	
	/*************************退款执行*********END***********************/
	
	/*************************报告推送*********OPEN***********************/
	
	public static String response_push_direct_exchange;
	
	public static String response_push_ttl_routing_key;
	
	@Value("${rabbit.order.response.push.direct.exchange}")
	public void setResponsePushDirectExchange(String response_push_direct_exchange) {
		Assert.notNull(response_push_direct_exchange, "response_push_direct_exchange can not be null");
		RabbitConfigure.response_push_direct_exchange = response_push_direct_exchange;
	}
	
	@Value("${rabbit.order.response.push.ttl.routingKey}")
	public void setResponsePushTtlRoutingKey(String response_push_ttl_routing_key) {
		Assert.notNull(response_push_ttl_routing_key, "response_push_ttl_routing_key can not be null");
		RabbitConfigure.response_push_ttl_routing_key = response_push_ttl_routing_key;
	}
	
	/*************************报告推送*********END***********************/

	
	
}
