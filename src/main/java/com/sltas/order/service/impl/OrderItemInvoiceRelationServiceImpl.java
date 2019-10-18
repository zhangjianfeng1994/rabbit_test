package com.sltas.order.service.impl;

import com.sltas.order.domain.OrderItemInvoiceRelation;
import com.sltas.order.mapper.OrderItemInvoiceRelationMapper;
import com.sltas.order.service.IOrderItemInvoiceRelationService;
import com.sltas.order.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发票子项关系表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-03-04
 */
@Service("orderItemInvoiceRelationService")
public class OrderItemInvoiceRelationServiceImpl extends ServiceImpl<OrderItemInvoiceRelationMapper, OrderItemInvoiceRelation> implements IOrderItemInvoiceRelationService {

}
