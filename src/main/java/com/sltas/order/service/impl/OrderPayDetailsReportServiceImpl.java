package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderPayDetailsReport;
import com.sltas.order.mapper.OrderPayDetailsReportMapper;
import com.sltas.order.service.IOrderPayDetailsReportService;

/**
 * <p>
 * 订单支付详情报告表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderPayDetailsReportService")
public class OrderPayDetailsReportServiceImpl extends ServiceImpl<OrderPayDetailsReportMapper, OrderPayDetailsReport> implements IOrderPayDetailsReportService {

}
