package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderDeductionsPayReport;
import com.sltas.order.mapper.OrderDeductionsPayReportMapper;
import com.sltas.order.service.IOrderDeductionsPayReportService;

/**
 * <p>
 * 批扣订单支付报告表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderDeductionsPayReportService")
public class OrderDeductionsPayReportServiceImpl extends ServiceImpl<OrderDeductionsPayReportMapper, OrderDeductionsPayReport> implements IOrderDeductionsPayReportService {

}
