package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderDeductionsItemExtra;
import com.sltas.order.mapper.OrderDeductionsItemExtraMapper;
import com.sltas.order.service.IOrderDeductionsItemExtraService;

/**
 * <p>
 * 批扣订单子项附属表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderDeductionsItemExtraService")
public class OrderDeductionsItemExtraServiceImpl extends ServiceImpl<OrderDeductionsItemExtraMapper, OrderDeductionsItemExtra> implements IOrderDeductionsItemExtraService {

}
