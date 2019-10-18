package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderBatchUnknown;
import com.sltas.order.mapper.OrderBatchUnknownMapper;
import com.sltas.order.service.IOrderBatchUnknownService;

/**
 * <p>
 * 订单批扣批次未知检索表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderBatchUnknownService")
public class OrderBatchUnknownServiceImpl extends ServiceImpl<OrderBatchUnknownMapper, OrderBatchUnknown> implements IOrderBatchUnknownService {

}
