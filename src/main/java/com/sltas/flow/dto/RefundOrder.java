package com.sltas.flow.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.Gson;

/**
 * <p>
 * Title: RefundOrderDto.java
 * </p>
 * <p>
 * Description: 退款测试类
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年10月30日 上午9:22:23  
 */
public class RefundOrder implements Serializable{

	/**
	 * @Fields serialVersionUID: TODO
	 * @author 周顺宇 
	 * @date 2018年10月30日 上午10:59:17 
	 */
	private static final long serialVersionUID = -5177714607920870768L;
	//唯一流水
	private String transNo;
	//订单描述
	private String describe;
	//退费金额
	private BigDecimal refundAmount;
	//启用开票
	private Boolean startInvoice;
	//创建时间
	private Date createdTime;
	
	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}


	public Boolean getStartInvoice() {
		return startInvoice;
	}

	public void setStartInvoice(Boolean startInvoice) {
		this.startInvoice = startInvoice;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
	

}
