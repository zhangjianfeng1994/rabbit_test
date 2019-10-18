package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundManageDetails;
import com.sltas.order.mapper.OrderRefundManageDetailsMapper;
import com.sltas.order.service.IOrderRefundManageDetailsService;

/**
 * <p>
 * 退款管理详情表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundManageDetailsService")
public class OrderRefundManageDetailsServiceImpl extends ServiceImpl<OrderRefundManageDetailsMapper, OrderRefundManageDetails> implements IOrderRefundManageDetailsService {

}
