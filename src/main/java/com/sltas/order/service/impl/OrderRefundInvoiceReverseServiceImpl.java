package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundInvoiceReverse;
import com.sltas.order.mapper.OrderRefundInvoiceReverseMapper;
import com.sltas.order.service.IOrderRefundInvoiceReverseService;

/**
 * <p>
 * 退票冲正表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundInvoiceReverseService")
public class OrderRefundInvoiceReverseServiceImpl extends ServiceImpl<OrderRefundInvoiceReverseMapper, OrderRefundInvoiceReverse> implements IOrderRefundInvoiceReverseService {

}
