package com.sltas.flow.util.exception;

import com.sltas.flow.util.api.ServiceErrorEnum;

/**
 * <p>
 * Title: ParamsCheckException.java
 * </p>
 * <p>
 * Description: 参数检测异常
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年11月6日 下午5:06:22  
 */
public class ParamsCheckException extends CustomException{
	
	/**
	 * @Fields serialVersionUID: TODO
	 * @author 周顺宇 
	 * @date 2018年11月6日 下午5:00:40 
	 */
	private static final long serialVersionUID = 1177966067954202587L;
	
	//异常枚举
	private ServiceErrorEnum serviceErrorEnum;
	
	public ParamsCheckException(ServiceErrorEnum serviceErrorEnum){
		super(serviceErrorEnum.getReturnmsg());
		this.serviceErrorEnum = serviceErrorEnum;
	}
	
//	public CheckCustomException(Throwable cause){
//		super(cause);
//	}
	
	public ParamsCheckException(ServiceErrorEnum serviceErrorEnum,Throwable cause){
		super(serviceErrorEnum.getReturnmsg(),cause);
		this.serviceErrorEnum = serviceErrorEnum;
	}

	public ServiceErrorEnum getServiceErrorEnum() {
		return serviceErrorEnum;
	}
	
}
