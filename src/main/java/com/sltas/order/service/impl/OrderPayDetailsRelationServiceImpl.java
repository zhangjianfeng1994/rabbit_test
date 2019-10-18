package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderPayDetailsRelation;
import com.sltas.order.mapper.OrderPayDetailsRelationMapper;
import com.sltas.order.service.IOrderPayDetailsRelationService;

/**
 * <p>
 * 订单支付详情关系表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderPayDetailsRelationService")
public class OrderPayDetailsRelationServiceImpl extends ServiceImpl<OrderPayDetailsRelationMapper, OrderPayDetailsRelation> implements IOrderPayDetailsRelationService {

}
