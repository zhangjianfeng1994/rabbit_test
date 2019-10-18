package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderReviewTimeLimit;
import com.sltas.order.mapper.OrderReviewTimeLimitMapper;
import com.sltas.order.service.IOrderReviewTimeLimitService;

/**
 * <p>
 * 订单审核时限表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderReviewTimeLimitService")
public class OrderReviewTimeLimitServiceImpl extends ServiceImpl<OrderReviewTimeLimitMapper, OrderReviewTimeLimit> implements IOrderReviewTimeLimitService {

}
