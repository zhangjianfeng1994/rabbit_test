package com.sltas.example.flow.rabbit.xml;

import java.io.Serializable;
import java.util.Date;

public class RefundPojo implements Serializable{

	/**
	 * @Fields serialVersionUID: TODO
	 * @author 周顺宇 
	 * @date 2018年10月18日 下午3:10:46 
	 */
	private static final long serialVersionUID = -3563003965908982584L;

	private String id;
	
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "RefundPojo [id=" + id + ", createTime=" + createTime + "]";
	}
	
	
	
}
