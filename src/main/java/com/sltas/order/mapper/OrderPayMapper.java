package com.sltas.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sltas.order.domain.OrderPay;

/**
 * <p>
 * 订单支付表 Mapper 接口
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
public interface OrderPayMapper extends BaseMapper<OrderPay> {

	/**
	 * 单笔对账——查询订单头下最后一笔支付信息
	 * @param selectOrderPay
	 * @return
	 */
	OrderPay searchLastOrderPaymene(OrderPay orderPay);
	
}
