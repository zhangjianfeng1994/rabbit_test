package com.sltas.order.service.impl;

import com.sltas.order.domain.OrderBackBatchDetails;
import com.sltas.order.mapper.OrderBackBatchDetailsMapper;
import com.sltas.order.service.IOrderBackBatchDetailsService;
import com.sltas.order.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单批量补录详情表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-04-08
 */
@Service("orderBackBatchDetailsService")
public class OrderBackBatchDetailsServiceImpl extends ServiceImpl<OrderBackBatchDetailsMapper, OrderBackBatchDetails> implements IOrderBackBatchDetailsService {

}
