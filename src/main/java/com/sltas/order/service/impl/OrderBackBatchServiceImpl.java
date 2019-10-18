package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderBackBatch;
import com.sltas.order.mapper.OrderBackBatchMapper;
import com.sltas.order.service.IOrderBackBatchService;

/**
 * <p>
 * 订单补录批次表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderBackBatchService")
public class OrderBackBatchServiceImpl extends ServiceImpl<OrderBackBatchMapper, OrderBackBatch> implements IOrderBackBatchService {

}
