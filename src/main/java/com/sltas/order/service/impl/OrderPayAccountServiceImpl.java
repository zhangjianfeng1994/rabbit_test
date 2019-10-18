package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderPayAccount;
import com.sltas.order.mapper.OrderPayAccountMapper;
import com.sltas.order.service.IOrderPayAccountService;

/**
 * <p>
 * 订单支付对账表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderPayAccountService")
public class OrderPayAccountServiceImpl extends ServiceImpl<OrderPayAccountMapper, OrderPayAccount> implements IOrderPayAccountService {

}
