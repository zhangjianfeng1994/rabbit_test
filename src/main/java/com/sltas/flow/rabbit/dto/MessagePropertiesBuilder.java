package com.sltas.flow.rabbit.dto;

import org.springframework.util.Assert;

/**
 * <p>
 * Title: MessagePropertiesBuilder.java
 * </p>
 * <p>
 * Description: rabbit 消息编辑器
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年10月31日 下午2:22:21  
 */
public class MessagePropertiesBuilder {

	private MessageProperties properties = new MessageProperties();
	
	private WaitConfirm waitConfirm; 
	
	private MessagePropertiesBuilder(){
		super();
	}
	
	public static MessagePropertiesBuilder newInstance() {
		return new MessagePropertiesBuilder();
	}
	
	public String getCorrelationDataId() {
		return this.properties.getCorrelationDataId();
	}

	public MessagePropertiesBuilder setCorrelationDataId(String correlationDataId) {
		this.properties.setCorrelationDataId(correlationDataId);
		return this;
	}

	public String getServiceId() {
		return this.properties.getServiceId();
	}

	public MessagePropertiesBuilder setServiceId(String serviceId) {
		this.properties.setServiceId(serviceId);
		return this;
	}

	public String getTargetExchange() {
		return this.properties.getTargetExchange();
	}

	public MessagePropertiesBuilder setTargetExchange(String targetExchange) {
		this.properties.setTargetExchange(targetExchange);
		return this;
	}

	public String getTargetRoutingKey() {
		return this.getTargetRoutingKey();
	}

	public MessagePropertiesBuilder setTargetRoutingKey(String targetRoutingKey) {
		this.properties.setTargetRoutingKey(targetRoutingKey);
		return this;
	}

	public Integer getPriority() {
		return this.properties.getPriority();
	}

	public MessagePropertiesBuilder setPriority(Integer priority) {
		this.properties.setPriority(priority);
		return this;
	}

	public String getExpiration() {
		return this.properties.getExpiration();
	}

	public MessagePropertiesBuilder setExpiration(String expiration) {
		this.properties.setExpiration(expiration);
		return this;
	}

	public Object getBody() {
		return this.properties.getBody();
	}

	public MessagePropertiesBuilder setBody(Object body) {
		this.properties.setBody(body);
		return this;
	}
	
	public Boolean getOpenCache() {
		return this.properties.getOpenCache();
	}

	public MessagePropertiesBuilder setOpenCache(Boolean openCache) {
		this.properties.setOpenCache(openCache);
		return this;
	}
	
	public WaitConfirm getWaitConfirm() {
		return this.waitConfirm;
	}

	public WaitConfirm setWaitConfirm(Boolean isWait) {
		this.waitConfirm = new WaitConfirm(this,isWait);
		return this.waitConfirm;
	}
	
	public MessageProperties build() {
		
		Assert.notNull(this.properties.getServiceId(), "serviceId can not be null");
		Assert.notNull(this.properties.getCorrelationDataId(), "correlationDataId can not be null");
		Assert.notNull(this.properties.getTargetExchange(), "targetExchange can not be null");
		Assert.notNull(this.properties.getTargetRoutingKey(), "routingKey can not be null");
		Assert.notNull(this.properties.getBody(), "body can not be null");
		
		if(this.waitConfirm != null){
			this.properties.setWaitResponse(this.waitConfirm.waitResponse);
			this.properties.setWaitTime(this.waitConfirm.waitTime);
		}
		return this.properties;
	}
	
	public static class WaitConfirm {

		public static final transient Boolean DEFAULT_CONFIRM = false;
		
		public static final transient Integer DEFAULT_TIME = 10;
		
		private volatile Boolean waitResponse = DEFAULT_CONFIRM;
		
		private volatile Integer waitTime = DEFAULT_TIME;
		
		private volatile transient MessagePropertiesBuilder messagePropertiesBuilder;

		public WaitConfirm(MessagePropertiesBuilder messagePropertiesBuilder,Boolean waitResponse){
			this.messagePropertiesBuilder = messagePropertiesBuilder;
			this.waitResponse = waitResponse;
		}
		
		public Boolean getWaitResponse() {
			return waitResponse;
		}

		public Integer getWaitTime() {
			return waitTime;
		}

		public MessagePropertiesBuilder setWaitTime(Integer waitTime) {
			this.waitTime = waitTime;
			return this.messagePropertiesBuilder;
		}

	}
	
}
