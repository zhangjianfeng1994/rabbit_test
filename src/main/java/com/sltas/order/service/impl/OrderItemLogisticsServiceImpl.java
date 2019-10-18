package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderItemLogistics;
import com.sltas.order.mapper.OrderItemLogisticsMapper;
import com.sltas.order.service.IOrderItemLogisticsService;

/**
 * <p>
 * 订单物流信息表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderItemLogisticsService")
public class OrderItemLogisticsServiceImpl extends ServiceImpl<OrderItemLogisticsMapper, OrderItemLogistics> implements IOrderItemLogisticsService {

}
