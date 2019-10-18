package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderPayTimeLimit;
import com.sltas.order.mapper.OrderPayTimeLimitMapper;
import com.sltas.order.service.IOrderPayTimeLimitService;

/**
 * <p>
 * 订单支付时限表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderPayTimeLimitService")
public class OrderPayTimeLimitServiceImpl extends ServiceImpl<OrderPayTimeLimitMapper, OrderPayTimeLimit> implements IOrderPayTimeLimitService {

}
