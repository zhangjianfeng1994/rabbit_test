package com.sltas.flow.util.api;

import java.io.Serializable;

import com.google.gson.Gson;
import com.sltas.flow.util.UtilDate;


/**
 * <p>
 * Title: CommonResponseHeader.java
 * </p>
 * <p>
 * Description: 订单公共响应结果
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年10月30日 上午10:19:41 
 * @param <T> 
 */
public class CommonResponseHeader<T>  implements Serializable {
	
	/**
	 * serialVersionUID
	 * @author 周顺宇
	 * @date 2017年6月27日下午8:01:13
	 */
	private static final long serialVersionUID = 2137912383693002178L;

	/**
	 * 返回码类型
	 *  1、S-操作成功
	 *	2、F-操作失败
	 *	3、E-操作异常
	 *	4、R-操作未知
	 */
	private String returnType;
	
	/**
	 * 返回码
	 */
	private String returnCode;
	
	/**
	 * 返回信息
	 */
	private String returnMsg;
	
	/**
	 * 交易日期
	 */
	private String tradeDate;
	
	/**
	 * 交易时间
	 */
	private String tradeTime;
	
	/**
	 * 返回数据
	 */
	private T body;
	
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	
    /**
     * <p>
     * Title: serviceReturn
     * </p>
     * <p>
     * Description: 返回服务结果
     * </p>
     * @param @param result
     * @param @param body
     * @param @return 
     * @return CommonResponseHeader<T>
     * @throws
     * @author 周顺宇 
     * @date 2018年10月30日 上午10:24:05 
     */
    public static <T> CommonResponseHeader<T> serviceReturn(ResponseResult result,T body) {
    	CommonResponseHeader<T> header = new CommonResponseHeader<T>();
    	header.setReturnType(result.getResponseType());
    	header.setReturnCode(result.getReturncode());
    	header.setReturnMsg(result.getReturnmsg());
        String[] parseDate = UtilDate.parseStringDate();
        header.setTradeDate(parseDate[0]);
        header.setTradeTime(parseDate[1]);
        if(body!=null){header.setBody(body);}
        return header;
    }
    
	public String toString() {
		 Gson gson = new Gson();
		 return gson.toJson(this);
	 }
}
