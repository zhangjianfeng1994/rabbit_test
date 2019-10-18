package com.sltas.flow.util.api;

public enum ResponseResult {
	

	/**
	 * S	00000000	操作成功
	 */
	SUCCESS(ResponseType.S, "00000000", "操作成功"),

    
	/**
	 * E    ERROR_ORDER_30122001  参数传送异常
	 */
	ERROR_ORDER_22001(ResponseType.E , Domain.order + "22001" , "参数传送异常");
	
	
//	sltas_order系统错误编码---------------结束
	
	private ResponseResult(String responseType, String returncode, String returnmsg) {
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



