package com.sltas.flow.util.exception;

public class CustomException extends RuntimeException {

	/**
	 * @Fields serialVersionUID: TODO
	 * @author 周顺宇 
	 * @date 2018年11月6日 下午4:55:58 
	 */
	private static final long serialVersionUID = -3898567229338056230L;

	public CustomException(String message) {
		super(message);
	}

	public CustomException(Throwable cause) {
		super(cause);
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
