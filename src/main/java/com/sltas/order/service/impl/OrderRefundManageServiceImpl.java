package com.sltas.order.service.impl;

import org.springframework.stereotype.Service;

import com.sltas.order.domain.OrderRefundManage;
import com.sltas.order.mapper.OrderRefundManageMapper;
import com.sltas.order.service.IOrderRefundManageService;

/**
 * <p>
 * 退款管理表 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
@Service("orderRefundManageService")
public class OrderRefundManageServiceImpl extends ServiceImpl<OrderRefundManageMapper, OrderRefundManage> implements IOrderRefundManageService {

}
