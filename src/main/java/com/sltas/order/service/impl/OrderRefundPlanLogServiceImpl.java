package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundPlanLog;
import com.sltas.order.mapper.OrderRefundPlanLogMapper;
import com.sltas.order.service.IOrderRefundPlanLogService;

/**
 * <p>
 * 订单退款进度历史表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundPlanLogService")
public class OrderRefundPlanLogServiceImpl extends ServiceImpl<OrderRefundPlanLogMapper, OrderRefundPlanLog> implements IOrderRefundPlanLogService {

}
