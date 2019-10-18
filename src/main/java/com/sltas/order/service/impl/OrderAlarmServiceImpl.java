package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderAlarm;
import com.sltas.order.mapper.OrderAlarmMapper;
import com.sltas.order.service.IOrderAlarmService;

/**
 * <p>
 * 系统告警表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderAlarmService")
public class OrderAlarmServiceImpl extends ServiceImpl<OrderAlarmMapper, OrderAlarm> implements IOrderAlarmService {

}
