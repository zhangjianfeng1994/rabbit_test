package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderReversalLog;
import com.sltas.order.mapper.OrderReversalLogMapper;
import com.sltas.order.service.IOrderReversalLogService;

/**
 * <p>
 * 订单冲正历史表  服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderReversalLogService")
public class OrderReversalLogServiceImpl extends ServiceImpl<OrderReversalLogMapper, OrderReversalLog> implements IOrderReversalLogService {

}
