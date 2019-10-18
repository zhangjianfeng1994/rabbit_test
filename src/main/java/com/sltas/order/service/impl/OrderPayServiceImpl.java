package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderPay;
import com.sltas.order.mapper.OrderPayMapper;
import com.sltas.order.service.IOrderPayService;

/**
 * <p>
 * 订单支付表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderPayService")
public class OrderPayServiceImpl extends ServiceImpl<OrderPayMapper, OrderPay> implements IOrderPayService {

	
	/**
	 * 单笔对账——查询订单头下最后一笔支付信息
	 * @param selectOrderPay
	 * @return
	 */
	@Override
	public OrderPay searchLastOrderPaymene(OrderPay orderPay) {
		return this.baseMapper.searchLastOrderPaymene(orderPay);
	}
	
	

}
