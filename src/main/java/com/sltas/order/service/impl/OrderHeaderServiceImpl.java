package com.sltas.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderHeader;
import com.sltas.order.mapper.OrderHeaderMapper;
import com.sltas.order.service.IOrderHeaderService;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderHeaderService")
public class OrderHeaderServiceImpl extends ServiceImpl<OrderHeaderMapper, OrderHeader> implements IOrderHeaderService {

	@Autowired
	private OrderHeaderMapper orderHeaderMapper;
	
	/**
	 * 单笔对账——更新订单头数据
	 * @param orderHeader
	 * @return
	 */
	@Override
	public int updateExternalSuccessOrder(OrderHeader orderHeader) {
		return orderHeaderMapper.updateExternalSuccessOrder(orderHeader);
	}

}
