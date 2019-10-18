package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderAccountStatistics;
import com.sltas.order.mapper.OrderAccountStatisticsMapper;
import com.sltas.order.service.IOrderAccountStatisticsService;

/**
 * <p>
 * 对账统计表    服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderAccountStatisticsService")
public class OrderAccountStatisticsServiceImpl extends ServiceImpl<OrderAccountStatisticsMapper, OrderAccountStatistics> implements IOrderAccountStatisticsService {

}
