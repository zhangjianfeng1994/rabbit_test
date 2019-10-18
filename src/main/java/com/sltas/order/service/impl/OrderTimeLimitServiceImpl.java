package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderTimeLimit;
import com.sltas.order.mapper.OrderTimeLimitMapper;
import com.sltas.order.service.IOrderTimeLimitService;

/**
 * <p>
 * 订单平台业务时限 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderTimeLimitService")
public class OrderTimeLimitServiceImpl extends ServiceImpl<OrderTimeLimitMapper, OrderTimeLimit> implements IOrderTimeLimitService {

}
