package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundAccountLongError;
import com.sltas.order.mapper.OrderRefundAccountLongErrorMapper;
import com.sltas.order.service.IOrderRefundAccountLongErrorService;
import com.sltas.order.service.impl.ServiceImpl;


/**
 * <p>
 * 退费订单长款对账错误信息记录表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundAccountLongErrorService")
public class OrderRefundAccountLongErrorServiceImpl extends ServiceImpl<OrderRefundAccountLongErrorMapper, OrderRefundAccountLongError> implements IOrderRefundAccountLongErrorService {
	

}
