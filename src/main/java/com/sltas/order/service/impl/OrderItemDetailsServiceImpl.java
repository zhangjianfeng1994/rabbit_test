package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderItemDetails;
import com.sltas.order.mapper.OrderItemDetailsMapper;
import com.sltas.order.service.IOrderItemDetailsService;


/**
 * <p>
 * 订单子项详情表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderItemDetailsService")
public class OrderItemDetailsServiceImpl extends ServiceImpl<OrderItemDetailsMapper, OrderItemDetails> implements IOrderItemDetailsService {

		
}
