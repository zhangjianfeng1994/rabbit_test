package com.sltas.order.service.impl;

import com.sltas.order.domain.OrderItemInvoice;
import com.sltas.order.mapper.OrderItemInvoiceMapper;
import com.sltas.order.service.IOrderItemInvoiceService;
import com.sltas.order.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 开票表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-03-04
 */
@Service("orderItemInvoiceService")
public class OrderItemInvoiceServiceImpl extends ServiceImpl<OrderItemInvoiceMapper, OrderItemInvoice> implements IOrderItemInvoiceService {

}
