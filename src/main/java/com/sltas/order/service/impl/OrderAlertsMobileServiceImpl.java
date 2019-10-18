package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderAlertsMobile;
import com.sltas.order.mapper.OrderAlertsMobileMapper;
import com.sltas.order.service.IOrderAlertsMobileService;

/**
 * <p>
 * 订单报警手机号表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderAlertsMobileService")
public class OrderAlertsMobileServiceImpl extends ServiceImpl<OrderAlertsMobileMapper, OrderAlertsMobile> implements IOrderAlertsMobileService {

}
