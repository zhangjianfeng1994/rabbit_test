package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderStatusLog;
import com.sltas.order.mapper.OrderStatusLogMapper;
import com.sltas.order.service.IOrderStatusLogService;

/**
 * <p>
 * 订单状态流水表     服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderStatusLogService")
public class OrderStatusLogServiceImpl extends ServiceImpl<OrderStatusLogMapper, OrderStatusLog> implements IOrderStatusLogService {

}
