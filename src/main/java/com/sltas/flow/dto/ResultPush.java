package com.sltas.flow.dto;

import java.io.Serializable;

import com.google.gson.Gson;

public class ResultPush implements Serializable{

	/**
	 * @Fields serialVersionUID: TODO
	 * @author 周顺宇 
	 * @date 2018年10月30日 上午10:59:32 
	 */
	private static final long serialVersionUID = 7784405403666802154L;
	
	private String id;
	
	private String transNo;
	
	private String msg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
