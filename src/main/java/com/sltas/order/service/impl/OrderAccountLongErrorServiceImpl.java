package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderAccountLongError;
import com.sltas.order.mapper.OrderAccountLongErrorMapper;
import com.sltas.order.service.IOrderAccountLongErrorService;
import com.sltas.order.service.impl.ServiceImpl;



/**
 * <p>
 * 订单长款对账错误信息记录表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderAccountLongErrorService")
public class OrderAccountLongErrorServiceImpl extends ServiceImpl<OrderAccountLongErrorMapper, OrderAccountLongError> implements IOrderAccountLongErrorService {
	
}
