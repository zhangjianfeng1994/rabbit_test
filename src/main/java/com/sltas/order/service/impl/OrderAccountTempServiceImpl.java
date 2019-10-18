package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderAccountTemp;
import com.sltas.order.domain.OrderExternalAccountShortError;
import com.sltas.order.mapper.OrderAccountTempMapper;
import com.sltas.order.service.IOrderAccountTempService;
import com.sltas.order.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * <p>
 * 订单对账临时表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderAccountTempService")
public class OrderAccountTempServiceImpl extends ServiceImpl<OrderAccountTempMapper, OrderAccountTemp> implements IOrderAccountTempService {
	

}
