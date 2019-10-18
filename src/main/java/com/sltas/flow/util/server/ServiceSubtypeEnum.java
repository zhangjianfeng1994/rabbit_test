package com.sltas.flow.util.server;

/**
 * <p>
 * Title: ServiceSubtype.java
 * </p>
 * <p>
 * Description: 业务子类型
 * </p>
 * <p>
 * 
 *  ORDER_PAY 订单支付
 *  
 *  订单创建-单笔支付 (单)
 *  订单支付接收报告 (单) (注意二次回调)
 *  订单支付查询状态 (单) 
 *  订单再次支付 (单)
 *  订单结束交易 (单)
 *  
 *  订单审核 (单)
 *  订单调价 (单)
 *  订单超时处理 (单)
 *  订单物流变更 (批)
 *  
 *  订单创建-线上补录 (单)	(注意二次回调)
 *  订单创建-线上补录对账补差 (单) (注意二次回调)
 *  订单创建-线下补录 (单/批)
 *  订单状态变更 (单) (线上变更线下补录)
 *  
 *  订单创建-批扣支付(批)
 *  批扣接收报告 (单) (注意二次回调)
 *  批扣查询状态 (单)
 *  
 *  -------------------------------------------------------------------
 *  
 *  ORDER_REFUND 订单退费
 *  
 *  订单退费-用户发起 (单)
 *  订单退费-商户发起 (单)
 *  订单退费-第三方发起 (单)
 *  订单退费-商户受理 (单)
 *  订单退费-商户审批 (批) (特殊0元->直接完成)
 *  订单退费-取消 (单)
 *  订单退费接收报告 (单)
 *  订单退费查询状态 (单)
 *  订单退费-冲正 (单)
 *  
 *  订单退费-出纳流程 (批)
 *  订单退费-财务受理 (批)
 *  订单退费-审批至出纳 (批)
 *  
 *  ---------------------------------------------------------
 *  
 *  ORDER_INVOICE: 订单发票
 *  
 *  开票-主动开票 (批) (订单-》发票 根据支付结果进行 时间维度 / 条数维度 进行开票)
 *  开票-被动开票 (批) (发票-》订单 通过管理界面，进行开票，数据信息组织与主动相同)
 *  开票接收报告 (单)
 *  开票查询状态 (单)
 *  
 *  退票-主动退票 (作废+冲红) (订单-》发票 退款/冲正 引发主动退票)
 *  退票-被动退票 (作废+冲红) (发票-》订单 管理端 手动发起退票)
 *  退票-线下退票 (发票-》订单 无视发票信息，直接抹除)
 *  退票接收报告 (单)
 *  退票查询状态 (单)
 *  
 *  -----------------------------------------------------------
 *  
 *  ORDER_REPORT_PUSH: 订单报告推送
 *  
 *  -----------------------------------------------------------
 *  
 *  ORDER_ACCOUNT_CHECKING: 订单对账
 * 
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年10月26日 下午5:51:34  
 */
public enum ServiceSubtypeEnum {

	/************************ORDER_PAY 订单支付*************START***********************/
	/**
	 * 订单创建-单笔支付 (单)							P_001
	 */
	P_SINGLE_PAYMENT(ServiceType.ORDER_PAY,ServiceType.ORDER_PAY+SystemUtil.LINE+"001",null),
	/**
	 * 订单支付接收报告 (单) (注意二次回调)			P_002
	 */
	P_ACCEPTANCE_REPORT(ServiceType.ORDER_PAY,ServiceType.ORDER_PAY+SystemUtil.LINE+"002",null),
	/**
	 * 订单支付查询状态 (单) 							P_003
	 */
	P_QUERY_STATUS(ServiceType.ORDER_PAY,ServiceType.ORDER_PAY+SystemUtil.LINE+"003",null),
	
	/************************ORDER_PAY 订单支付*************END***********************/
	
	/************************ORDER_REFUND 订单退费*************START***********************/
	/**
	 * 订单退费-用户发起 (单)							R_100
	 */
	R_USER_SEND(ServiceType.ORDER_REFUND,ServiceType.ORDER_REFUND+SystemUtil.LINE+"100",null),
	/**
	 * 订单退费-商户发起 (单)							R_101
	 */
	R_MERCHANT_SEND(ServiceType.ORDER_REFUND,ServiceType.ORDER_REFUND+SystemUtil.LINE+"101",null),
	/**
	 * 订单退费-第三方发起 (单)						R_102
	 */
	R_THIRD_PARTY_SEND(ServiceType.ORDER_REFUND,ServiceType.ORDER_REFUND+SystemUtil.LINE+"102",ProducerNackConfiguration.newInstance("orderRefundService","refundExecuteNack")),
	
	/**
	 * 订单退费-出纳流程 (批)							R_110
	 */
	R_CASHIER(ServiceType.ORDER_REFUND,ServiceType.ORDER_REFUND+SystemUtil.LINE+"110",null),
	
	/************************ORDER_REFUND 订单退费*************END***********************/
	
	/************************ORDER_INVOICE: 订单发票*************START***********************/
	
	/************************ORDER_INVOICE: 订单发票*************END***********************/
	
	/************************ORDER_REPORT_PUSH: 订单报告推送*************START***********************/
	
	/**
	 * 订单创建-单笔支付 								S_200
	 */
	S_SINGLE_PAYMENT(ServiceType.ORDER_REPORT_SEND,ServiceType.ORDER_REPORT_SEND+SystemUtil.LINE+"200",null),
	
	/**
	 * 订单退费报告									S_201
	 */
	S_REFUND(ServiceType.ORDER_REPORT_SEND,ServiceType.ORDER_REPORT_SEND+SystemUtil.LINE+"201",null),
	
	
	/************************ORDER_REPORT_PUSH: 订单报告推送*************END***********************/
	
	/************************ORDER_ACCOUNT_CHECKING: 订单对账*************START***********************/
	
	/************************ORDER_ACCOUNT_CHECKING: 订单对账*************END***********************/
	
	A_ERROR(null,null,null);
	

	/**
	 * <p>
	 * Title: ServiceSubtype.java
	 * </p>
	 * <p>
	 * Description: TODO(describe the file) 
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @param serviceType					服务类型
	 * @param serviceId						服务子类型
	 * @param producerNackConfiguration 	rabbit入队异常处理配置信息
	 */
	private ServiceSubtypeEnum(String serviceType, String serviceId, ProducerNackConfiguration producerNackConfiguration) {
		this.serviceType = serviceType;
		this.serviceId = serviceId;
		this.producerNackConfiguration = producerNackConfiguration;
	}

	private String serviceType;
    private String serviceId;
    private ProducerNackConfiguration producerNackConfiguration;
    
	public String getServiceType() {
		return serviceType;
	}
	public String getServiceId() {
		return serviceId;
	}
	public ProducerNackConfiguration getProducerNackConfiguration() {
		return producerNackConfiguration;
	}

	
}
