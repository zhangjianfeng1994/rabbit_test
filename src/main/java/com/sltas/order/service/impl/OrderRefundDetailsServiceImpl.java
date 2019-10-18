package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundDetails;
import com.sltas.order.mapper.OrderRefundDetailsMapper;
import com.sltas.order.service.IOrderRefundDetailsService;
import com.sltas.order.service.impl.ServiceImpl;


/**
 * <p>
 * 订单退款详情表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundDetailsService")
public class OrderRefundDetailsServiceImpl extends ServiceImpl<OrderRefundDetailsMapper, OrderRefundDetails> implements IOrderRefundDetailsService {


}
