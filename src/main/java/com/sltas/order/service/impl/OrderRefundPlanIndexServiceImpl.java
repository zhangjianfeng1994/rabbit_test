package com.sltas.order.service.impl;

import com.sltas.order.domain.OrderRefundPlanIndex;
import com.sltas.order.mapper.OrderRefundPlanIndexMapper;
import com.sltas.order.service.IOrderRefundPlanIndexService;
import com.sltas.order.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单退款进度索引表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-03-04
 */
@Service("orderRefundPlanIndexService")
public class OrderRefundPlanIndexServiceImpl extends ServiceImpl<OrderRefundPlanIndexMapper, OrderRefundPlanIndex> implements IOrderRefundPlanIndexService {

}
