package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundAccountShortError;
import com.sltas.order.mapper.OrderRefundAccountShortErrorMapper;
import com.sltas.order.service.IOrderRefundAccountShortErrorService;

/**
 * <p>
 * 退费订单短款对账错误信息记录表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundAccountShortErrorService")
public class OrderRefundAccountShortErrorServiceImpl extends ServiceImpl<OrderRefundAccountShortErrorMapper, OrderRefundAccountShortError> implements IOrderRefundAccountShortErrorService {

}
