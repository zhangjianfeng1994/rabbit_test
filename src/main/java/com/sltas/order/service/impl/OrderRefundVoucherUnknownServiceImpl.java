package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundVoucherUnknown;
import com.sltas.order.mapper.OrderRefundVoucherUnknownMapper;
import com.sltas.order.service.IOrderRefundVoucherUnknownService;

/**
 * <p>
 * 退凭证未知表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundVoucherUnknownService")
public class OrderRefundVoucherUnknownServiceImpl extends ServiceImpl<OrderRefundVoucherUnknownMapper, OrderRefundVoucherUnknown> implements IOrderRefundVoucherUnknownService {

}
