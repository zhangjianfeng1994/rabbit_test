package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderDeductionsItem;
import com.sltas.order.mapper.OrderDeductionsItemMapper;
import com.sltas.order.service.IOrderDeductionsItemService;

/**
 * <p>
 * 批扣订单子项表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderDeductionsItemService")
public class OrderDeductionsItemServiceImpl extends ServiceImpl<OrderDeductionsItemMapper, OrderDeductionsItem> implements IOrderDeductionsItemService {

}
