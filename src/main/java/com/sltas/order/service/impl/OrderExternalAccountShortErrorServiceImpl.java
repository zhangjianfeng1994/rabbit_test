package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderExternalAccountShortError;
import com.sltas.order.mapper.OrderExternalAccountShortErrorMapper;
import com.sltas.order.service.IOrderExternalAccountShortErrorService;

/**
 * <p>
 * 补录外部订单短款对账错误信息记录表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderExternalAccountShortErrorService")
public class OrderExternalAccountShortErrorServiceImpl extends ServiceImpl<OrderExternalAccountShortErrorMapper, OrderExternalAccountShortError> implements IOrderExternalAccountShortErrorService {

}
