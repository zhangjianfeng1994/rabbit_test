package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderDeductionsHeader;
import com.sltas.order.mapper.OrderDeductionsHeaderMapper;
import com.sltas.order.service.IOrderDeductionsHeaderService;

/**
 * <p>
 * 批扣订单表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderDeductionsHeaderService")
public class OrderDeductionsHeaderServiceImpl extends ServiceImpl<OrderDeductionsHeaderMapper, OrderDeductionsHeader> implements IOrderDeductionsHeaderService {

}
