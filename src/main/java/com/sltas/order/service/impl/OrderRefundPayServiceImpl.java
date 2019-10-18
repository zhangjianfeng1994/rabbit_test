package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundPay;
import com.sltas.order.mapper.OrderRefundPayMapper;
import com.sltas.order.service.IOrderRefundPayService;

/**
 * <p>
 * 订单退款流水表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundPayService")
public class OrderRefundPayServiceImpl extends ServiceImpl<OrderRefundPayMapper, OrderRefundPay> implements IOrderRefundPayService {

}
