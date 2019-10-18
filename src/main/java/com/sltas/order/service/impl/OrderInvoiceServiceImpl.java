package com.sltas.order.service.impl;

import com.sltas.order.domain.OrderInvoice;
import com.sltas.order.mapper.OrderInvoiceMapper;
import com.sltas.order.service.IOrderInvoiceService;
import com.sltas.order.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发票表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-03-04
 */
@Service("orderInvoiceService")
public class OrderInvoiceServiceImpl extends ServiceImpl<OrderInvoiceMapper, OrderInvoice> implements IOrderInvoiceService {

}
