package com.sltas.order.util.id;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.sltas.order.util.DateTimeUtils;

/**
 * <p>
 * Title: OrderWorker.java
 * </p>
 * <p>
 * Description: 其中 TRANS_NO 与 ITEM_NO 为了业务上进行区分 各站1个字符
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2019年1月7日 下午6:11:58  
 */
public class OrderWorker {
	
	private OrderWorker() {};
	
	/**
	 * @Fields TRANS_NO: 仅针对正向订单号
	 * @author 周顺宇 
	 * @date 2019年1月2日 下午7:32:21 
	 */
	public final static String TRANS_NO = "1";
	
	/**
	 * @Fields ITEM_NO: 仅针对正向子项号
	 * @author 周顺宇 
	 * @date 2019年1月2日 下午7:32:24 
	 */
	public final static String ITEM_NO = "2";	
	
	/**
	 * @Fields ITEM_NO: 仅针对退款订单号
	 * @author 周顺宇 
	 * @date 2019年1月2日 下午7:32:24 
	 */
	public final static String REFUND_NO = "3";	
	
	/**
	 * @Fields id: 主键流水号
	 * @author 周顺宇 
	 * @date 2019年1月7日 下午3:24:23 
	 */
	private long id;
	
	/**
	 * @Fields no: 业务流水号
	 * @author 周顺宇 
	 * @date 2019年1月7日 下午3:24:53 
	 */
	private String no;
	
	/**
	 * @Fields gmtCreate: 创建时间
	 * @author 周顺宇 
	 * @date 2019年1月7日 下午3:25:01 
	 */
	private LocalDateTime gmtCreate;
	
	
	/**
	 * @Fields gmtDate: 创建日期
	 * @author 周顺宇 
	 * @date 2019年2月25日 下午5:58:29 
	 */
	private LocalDate gmtDate; 
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public LocalDate getGmtDate() {
		return gmtDate;
	}
	public void setGmtDate(LocalDate gmtDate) {
		this.gmtDate = gmtDate;
	}
	public LocalDateTime getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(LocalDateTime gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	public static OrderWorker initId() {
		OrderWorker ow = new OrderWorker();
		//优先获取主键ID
		ow.setId(IdWorker.getId());
		//通过主键ID获取时间戳
		ow.setGmtCreate(IdWorker.parseId(ow.getId()));
		//通过时间戳获取日期
		ow.setGmtDate(ow.getGmtCreate().toLocalDate());
		return ow;
	}
	
	public static OrderWorker initNo(String mark) {
		OrderWorker ow = new OrderWorker();
		//优先获取主键ID
		ow.setId(IdWorker.getId());
		//通过主键ID获取NO
		ow.setNo(IdWorker.getNo(mark, ow.getId()));
		//通过主键ID获取时间戳
		ow.setGmtCreate(IdWorker.parseId(ow.getId()));
		//通过时间戳获取日期
		ow.setGmtDate(ow.getGmtCreate().toLocalDate());
		return ow;
		
	}
	
    @Override
    public String toString() {
    	return new Gson().toJson(this);
    }
	
}
