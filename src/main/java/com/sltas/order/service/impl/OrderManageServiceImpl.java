package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderManage;
import com.sltas.order.mapper.OrderManageMapper;
import com.sltas.order.service.IOrderManageService;
import com.sltas.order.service.impl.ServiceImpl;


/**
 * <p>
 * 订单信息管理表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderManageService")
public class OrderManageServiceImpl extends ServiceImpl<OrderManageMapper, OrderManage> implements IOrderManageService {


}
