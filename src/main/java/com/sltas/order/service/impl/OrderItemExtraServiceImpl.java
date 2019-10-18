package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderItemExtra;
import com.sltas.order.mapper.OrderItemExtraMapper;
import com.sltas.order.service.IOrderItemExtraService;

/**
 * <p>
 * 订单子项附属表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderItemExtraService")
public class OrderItemExtraServiceImpl extends ServiceImpl<OrderItemExtraMapper, OrderItemExtra> implements IOrderItemExtraService {

}
