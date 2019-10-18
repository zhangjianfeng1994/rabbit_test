package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundAccountTemp;
import com.sltas.order.mapper.OrderRefundAccountTempMapper;
import com.sltas.order.service.IOrderRefundAccountTempService;
import com.sltas.order.service.impl.ServiceImpl;


/**
 * <p>
 * 退费订单对账临时表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundAccountTempService")
public class OrderRefundAccountTempServiceImpl extends ServiceImpl<OrderRefundAccountTempMapper, OrderRefundAccountTemp> implements IOrderRefundAccountTempService {

	
}
