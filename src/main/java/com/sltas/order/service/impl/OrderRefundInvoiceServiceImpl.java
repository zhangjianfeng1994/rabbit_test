package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundInvoice;
import com.sltas.order.mapper.OrderRefundInvoiceMapper;
import com.sltas.order.service.IOrderRefundInvoiceService;

/**
 * <p>
 * 退票表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundInvoiceService")
public class OrderRefundInvoiceServiceImpl extends ServiceImpl<OrderRefundInvoiceMapper, OrderRefundInvoice> implements IOrderRefundInvoiceService {

}
