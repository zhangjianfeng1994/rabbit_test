package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundPayReport;
import com.sltas.order.mapper.OrderRefundPayReportMapper;
import com.sltas.order.service.IOrderRefundPayReportService;
import com.sltas.order.service.impl.ServiceImpl;


/**
 * <p>
 * 订单退款报告表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundPayReportService")
public class OrderRefundPayReportServiceImpl extends ServiceImpl<OrderRefundPayReportMapper, OrderRefundPayReport> implements IOrderRefundPayReportService {


}
