package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundInvoiceUnknown;
import com.sltas.order.mapper.OrderRefundInvoiceUnknownMapper;
import com.sltas.order.service.IOrderRefundInvoiceUnknownService;

/**
 * <p>
 * 退票未知检索表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundInvoiceUnknownService")
public class OrderRefundInvoiceUnknownServiceImpl extends ServiceImpl<OrderRefundInvoiceUnknownMapper, OrderRefundInvoiceUnknown> implements IOrderRefundInvoiceUnknownService {

}
