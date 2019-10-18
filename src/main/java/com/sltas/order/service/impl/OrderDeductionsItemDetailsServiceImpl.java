package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderDeductionsItemDetails;
import com.sltas.order.mapper.OrderDeductionsItemDetailsMapper;
import com.sltas.order.service.IOrderDeductionsItemDetailsService;

/**
 * <p>
 * 批扣订单子项详情表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderDeductionsItemDetailsService")
public class OrderDeductionsItemDetailsServiceImpl extends ServiceImpl<OrderDeductionsItemDetailsMapper, OrderDeductionsItemDetails> implements IOrderDeductionsItemDetailsService {

}
