package com.sltas.flow.rabbit.dto;

import java.io.Serializable;

import com.google.gson.Gson;

/**
 * <p>
 * Title: NackCause.java
 * </p>
 * <p>
 * Description: 应答失败原因
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年11月2日 上午11:42:30  
 */
public class NackCause implements Serializable{
	
	/**
	 * @Fields serialVersionUID: TODO
	 * @author 周顺宇 
	 * @date 2018年11月5日 下午3:44:44 
	 */
	private static final long serialVersionUID = -5549506678423896749L;

	public final static String NACK_RETURN = "ReturnCallback";
	
	public final static String NACK_CONFIRM = "ConfirmCallback";

	/**
	 * @Fields nackType: 两种模式 NACK_RETURN（消息无法送达） NACK_CONFIRM（消息确认失败）
	 * @author 周顺宇 
	 * @date 2018年11月2日 下午2:20:14 
	 */
	private String nackType;
	
	private String correlationDataId;
	
	private String serviceId;
	
	private Object body;
	
	private String cause;
	
	/**
	 * @Fields returnNack: nackType = ReturnCallback 此值才存在
	 * @author 周顺宇 
	 * @date 2018年11月2日 下午2:07:10 
	 */
	private ReturnNack returnNack; 
	
	public String getNackType() {
		return nackType;
	}

	public void setNackType(String nackType) {
		this.nackType = nackType;
	}

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

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public ReturnNack getReturnNack() {
		return returnNack;
	}

	public void setReturnNack(ReturnNack returnNack) {
		this.returnNack = returnNack;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public static class ReturnNack implements Serializable{

		/**
		 * @Fields serialVersionUID: TODO
		 * @author 周顺宇 
		 * @date 2018年11月5日 下午3:45:09 
		 */
		private static final long serialVersionUID = 1953827176430455996L;

		private int replyCode; 
		
		private String replyText;
		
		private String exchange; 
		
		private String routingKey;

		public int getReplyCode() {
			return replyCode;
		}

		public void setReplyCode(int replyCode) {
			this.replyCode = replyCode;
		}

		public String getReplyText() {
			return replyText;
		}

		public void setReplyText(String replyText) {
			this.replyText = replyText;
		}

		public String getExchange() {
			return exchange;
		}

		public void setExchange(String exchange) {
			this.exchange = exchange;
		}

		public String getRoutingKey() {
			return routingKey;
		}

		public void setRoutingKey(String routingKey) {
			this.routingKey = routingKey;
		}

		@Override
		public String toString() {
			return new Gson().toJson(this);
		}
		
	}
	
}



