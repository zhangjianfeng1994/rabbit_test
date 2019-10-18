package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundVoucher;
import com.sltas.order.mapper.OrderRefundVoucherMapper;
import com.sltas.order.service.IOrderRefundVoucherService;

/**
 * <p>
 * 退凭证表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundVoucherService")
public class OrderRefundVoucherServiceImpl extends ServiceImpl<OrderRefundVoucherMapper, OrderRefundVoucher> implements IOrderRefundVoucherService {

}
