package com.sltas.flow.util.api;

public enum ServiceErrorEnum {
	
	
	/*************************************订单创建报错信息***************************************************/
	/**
	 * S	00000000	操作成功
	 */
	SUCCESS(ResponseType.S, "00000000", "操作成功"),

//	sltas_order系统错误编码---------------开始	
//	20001-29999	业务性错误-----------开始
    
	/**
	 * E    ERROR_ORDER_30122001  参数传送异常
	 */
	ERROR_ORDER_22001(ResponseType.E , Domain.order + "22001" , "参数传送异常");
	
//	sltas_order系统错误编码---------------结束
	
	private ServiceErrorEnum(String responseType, String returncode, String returnmsg) {
		this.responseType = responseType;
		this.returncode = returncode;
		this.returnmsg = returnmsg;
		
	}

	private String responseType;
    private String returncode;
    private String returnmsg;
    
	public String getResponseType() {
		return responseType;
	}
	public String getReturncode() {
		return returncode;
	}
	public String getReturnmsg() {
		return returnmsg;
	}
}



