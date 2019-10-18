package com.sltas.order.service;

import com.sltas.order.domain.OrderHeader;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
public interface IOrderHeaderService extends IService<OrderHeader> {

	/**
	 * 单笔对账——更新订单头数据
	 * @param orderHeader
	 * @return
	 */
	int updateExternalSuccessOrder(OrderHeader orderHeader);

}
