package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundPlan;
import com.sltas.order.mapper.OrderRefundPlanMapper;
import com.sltas.order.service.IOrderRefundPlanService;

/**
 * <p>
 * 订单退款进度表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundPlanService")
public class OrderRefundPlanServiceImpl extends ServiceImpl<OrderRefundPlanMapper, OrderRefundPlan> implements IOrderRefundPlanService {

}
