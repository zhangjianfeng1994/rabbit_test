package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderDeductionsPayNo;
import com.sltas.order.mapper.OrderDeductionsPayNoMapper;
import com.sltas.order.service.IOrderDeductionsPayNoService;

/**
 * <p>
 * 批扣订单支付表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderDeductionsPayNoService")
public class OrderDeductionsPayNoServiceImpl extends ServiceImpl<OrderDeductionsPayNoMapper, OrderDeductionsPayNo> implements IOrderDeductionsPayNoService {

}
