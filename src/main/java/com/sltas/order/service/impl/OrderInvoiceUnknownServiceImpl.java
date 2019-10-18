package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderInvoiceUnknown;
import com.sltas.order.mapper.OrderInvoiceUnknownMapper;
import com.sltas.order.service.IOrderInvoiceUnknownService;

/**
 * <p>
 * 开票未知检索表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderInvoiceUnknownService")
public class OrderInvoiceUnknownServiceImpl extends ServiceImpl<OrderInvoiceUnknownMapper, OrderInvoiceUnknown> implements IOrderInvoiceUnknownService {

}
