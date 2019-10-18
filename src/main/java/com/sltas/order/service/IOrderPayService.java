package com.sltas.order.service;

import com.sltas.order.domain.OrderPay;

/**
 * <p>
 * 订单支付表 服务类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
public interface IOrderPayService extends IService<OrderPay> {

	/**
	 * 单笔对账——查询订单头下最后一笔支付信息
	 * @param selectOrderPay
	 * @return
	 */
	OrderPay searchLastOrderPaymene(OrderPay selectOrderPay);

	
}
