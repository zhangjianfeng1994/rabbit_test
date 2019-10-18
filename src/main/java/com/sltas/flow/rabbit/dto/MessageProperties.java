package com.sltas.flow.rabbit.dto;

import java.io.Serializable;

import com.google.gson.Gson;

/**
 * <p>
 * Title: PreMessage.java
 * </p>
 * <p>
 * Description: rabbit 发送消息实体
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年10月30日 下午6:31:56 
 * @param <T> 
 */
public class MessageProperties implements Serializable {

	/**
	 * @Fields serialVersionUID: TODO
	 * @author 周顺宇 
	 * @date 2018年10月31日 上午10:59:43 
	 */
	private static final long serialVersionUID = -2897090462646603051L;

	/**
	 * Durability：是否持久化，Durable是，Transient是否。如果不持久化，那么在服务器宕机或重启之后Queue就会丢失。
	 * Auto delete：如果选择yes，当最后一个消费者不在监听Queue的时候，该Queue就会自动删除，一般选择false。
	 * Arguments：AMQP协议留给AMQP实现者扩展使用的。
	 * 		x-message-ttl：一个消息推送到队列中的存活时间。设置的值之后还没消费就会被删除。
	 * 		x-expires：在自动删除该队列的时候，可以使用该队列的时间。
	 * 		x-max-length：在队列头部删除元素之前，队列可以包含多少个（就绪）消息，如果再次向队列中发送消息，会删除最早的那条消息，用来控制队列中消息的数量。
	 * 		x-max-length-bytes：在队列头部删除元素之前，队列的总消息体的大小，用来控制队列中消息的总大小。
	 * 		x-dead-letter-exchange：当消息被拒绝或者消息过期，消息重新发送到的交换机（Exchange）的可选名称。
	 * 		x-dead-letter-routing-key：当消息被拒绝或者消息过期，消息重新发送到的交换机绑定的Route key的名称，如果没有设置则使用之前的Route key。
	 * 		x-max-priority：队列支持的最大优先级数，如果没有设置则不支持消息优先级
	 * 		x-queue-mode：将队列设置为延迟模式，在磁盘上保留尽可能多的消息以减少RAM使用; 如果未设置，队列将保持在内存中的缓存，以尽可能快地传递消息。
	 * 		x-queue-master-locator：将队列设置为主位置模式，确定在节点集群上声明队列主节点所在的规则。
	 */
	/**
	 * 消息。服务器和应用程序之间传送的数据，本质上就是一段数据，由Properties和Payload(body)组成。
	 * 
	 * Delivery mode：是否持久化，如果未设置持久化，转发到queue中并未消费则重启服务或者服务宕机则消息丢失。
	 * Headers：头信息，是由一个或多个健值对组成的，当固定的Properties不满足我们需要的时候，可以自己扩展。
	 * 
	 * Properties（属性）
	 * content_type：传输协议
	 * content_encoding：编码方式
	 * priority：优先级
	 * correlation_id：rpc属性，请求的唯一标识。
	 * reply_to：rpc属性，
	 * expiration：消息的过期时间
	 * message_id：消息的id
	 * timestamp：消息的时间戳
	 * 
	 * 如何保证消息的不丢失，三个地方做到持久化。
	 * 
	 * Exchange需要持久化。
	 * Queue需要持久化。
	 * Message需要持久化。
	 */
	
	public static final transient Integer DEFAULT_PRIORITY = 0;
	
	public static final transient Boolean DEFAULT_CONFIRM = false;
	
	public static final transient Integer DEFAULT_TIME = 10;
	
	private volatile String correlationDataId;
	
	private volatile String serviceId;
	
	private volatile String targetExchange;
	
	private volatile String targetRoutingKey;
	
	private volatile Integer priority = DEFAULT_PRIORITY;
	
	private volatile String expiration;
	
	private volatile Boolean waitResponse = DEFAULT_CONFIRM;
	
	private volatile Integer waitTime = DEFAULT_TIME;
	
	private volatile Boolean openCache = DEFAULT_CONFIRM;
	
	private volatile Object body;
	
	public String getCorrelationDataId() {
		return correlationDataId;
	}

	public void setCorrelationDataId(String correlationDataId) {
		this.correlationDataId = correlationDataId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getTargetExchange() {
		return targetExchange;
	}

	public void setTargetExchange(String targetExchange) {
		this.targetExchange = targetExchange;
	}

	public String getTargetRoutingKey() {
		return targetRoutingKey;
	}

	public void setTargetRoutingKey(String targetRoutingKey) {
		this.targetRoutingKey = targetRoutingKey;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public Boolean getWaitResponse() {
		return waitResponse;
	}

	public void setWaitResponse(Boolean waitResponse) {
		this.waitResponse = waitResponse;
	}

	public Integer getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(Integer waitTime) {
		this.waitTime = waitTime;
	}
	
	public Boolean getOpenCache() {
		return openCache;
	}

	public void setOpenCache(Boolean openCache) {
		this.openCache = openCache;
	}
	
	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}
