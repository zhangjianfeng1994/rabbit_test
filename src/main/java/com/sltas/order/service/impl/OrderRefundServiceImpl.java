package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefund;
import com.sltas.order.mapper.OrderRefundMapper;
import com.sltas.order.service.IOrderRefundService;
import com.sltas.order.service.impl.ServiceImpl;


/**
 * <p>
 * 订单退款表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundService")
public class OrderRefundServiceImpl extends ServiceImpl<OrderRefundMapper, OrderRefund> implements IOrderRefundService {


}
