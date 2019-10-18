package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderBatch;
import com.sltas.order.mapper.OrderBatchMapper;
import com.sltas.order.service.IOrderBatchService;

/**
 * <p>
 * 订单批扣批次表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderBatchService")
public class OrderBatchServiceImpl extends ServiceImpl<OrderBatchMapper, OrderBatch> implements IOrderBatchService {

}
