package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderPayDetails;
import com.sltas.order.mapper.OrderPayDetailsMapper;
import com.sltas.order.service.IOrderPayDetailsService;

/**
 * <p>
 * 订单支付流水详情表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderPayDetailsService")
public class OrderPayDetailsServiceImpl extends ServiceImpl<OrderPayDetailsMapper, OrderPayDetails> implements IOrderPayDetailsService {

}
