package com.sltas.jibx.v3;

import lombok.Data;

@Data
public class VoucherHead {

	/**
	 * 是否预收预付标志 非空字段
	 */
	private String prepay;
	
	/**
	 * 单位编码  非空字段
	 */
	private String corp;
	
	/**
	 * 业务类型 非空字段，D4是收款结算单,D5是付款结算单,D6是划账结算单,sysid都应该为2该数据项需要需要基础数据对照(单据类型)
	 */
	private String billTypeCode;
	private String businessType;
	
	private String billDate;
	
	private String sysid;
}
