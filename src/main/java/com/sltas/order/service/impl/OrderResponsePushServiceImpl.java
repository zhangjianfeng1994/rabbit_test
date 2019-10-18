package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderResponsePush;
import com.sltas.order.mapper.OrderResponsePushMapper;
import com.sltas.order.service.IOrderResponsePushService;

/**
 * <p>
 * 结果报告推送表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderResponsePushService")
public class OrderResponsePushServiceImpl extends ServiceImpl<OrderResponsePushMapper, OrderResponsePush> implements IOrderResponsePushService {

}
