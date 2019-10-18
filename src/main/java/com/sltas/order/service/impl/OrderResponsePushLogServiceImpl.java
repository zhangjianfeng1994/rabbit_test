package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderResponsePushLog;
import com.sltas.order.mapper.OrderResponsePushLogMapper;
import com.sltas.order.service.IOrderResponsePushLogService;

/**
 * <p>
 * 结果报告历史表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderResponsePushLogService")
public class OrderResponsePushLogServiceImpl extends ServiceImpl<OrderResponsePushLogMapper, OrderResponsePushLog> implements IOrderResponsePushLogService {

}
