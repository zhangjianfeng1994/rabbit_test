package com.sltas.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sltas.order.domain.OrderHeader;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author zsy
 * @since 2019-01-03
 */
public interface OrderHeaderMapper extends BaseMapper<OrderHeader> {

	@Select("select * from order_header ${ew.customSqlSegment}")
	List<OrderHeader> findAll(@Param("ew") Wrapper wrapper);

	/**
	 * 单笔对账——更新订单头数据
	 * @param orderHeader
	 * @return
	 */
	int updateExternalSuccessOrder(OrderHeader orderHeader);

}
