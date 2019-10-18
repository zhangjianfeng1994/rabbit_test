package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundPayUnknown;
import com.sltas.order.mapper.OrderRefundPayUnknownMapper;
import com.sltas.order.service.IOrderRefundPayUnknownService;

/**
 * <p>
 * 订单退款检索表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundPayUnknownService")
public class OrderRefundPayUnknownServiceImpl extends ServiceImpl<OrderRefundPayUnknownMapper, OrderRefundPayUnknown> implements IOrderRefundPayUnknownService {

}
