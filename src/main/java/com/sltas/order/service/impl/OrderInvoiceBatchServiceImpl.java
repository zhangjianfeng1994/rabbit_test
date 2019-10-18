package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderInvoiceBatch;
import com.sltas.order.mapper.OrderInvoiceBatchMapper;
import com.sltas.order.service.IOrderInvoiceBatchService;

/**
 * <p>
 * 开票批次表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderInvoiceBatchService")
public class OrderInvoiceBatchServiceImpl extends ServiceImpl<OrderInvoiceBatchMapper, OrderInvoiceBatch> implements IOrderInvoiceBatchService {

}
