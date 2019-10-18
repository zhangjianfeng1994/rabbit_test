package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderPayReport;
import com.sltas.order.mapper.OrderPayReportMapper;
import com.sltas.order.service.IOrderPayReportService;

/**
 * <p>
 * 订单支付报告表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderPayReportService")
public class OrderPayReportServiceImpl extends ServiceImpl<OrderPayReportMapper, OrderPayReport> implements IOrderPayReportService {

}
