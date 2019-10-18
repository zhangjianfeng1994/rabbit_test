package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderAccountShortError;
import com.sltas.order.mapper.OrderAccountShortErrorMapper;
import com.sltas.order.service.IOrderAccountShortErrorService;

/**
 * <p>
 * 订单短款对账错误信息记录表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderAccountShortErrorService")
public class OrderAccountShortErrorServiceImpl extends ServiceImpl<OrderAccountShortErrorMapper, OrderAccountShortError> implements IOrderAccountShortErrorService {

}
