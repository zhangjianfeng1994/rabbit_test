package com.sltas.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.gson.Gson;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单补录批次表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderBackBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 业务类型（1 - BILLS_ORDER账单订单；2 - SALES_ORDER销售订单；3 - RECHARGE_ORDER充值订单；4 - TRANSFER_ORDER转账订单；）
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 商量内部系统批次请求流水
     */
    @TableField("third_party_no")
    private String thirdPartyNo;

    /**
     * 总金额 单位： 元
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 总笔数
     */
    @TableField("total_count")
    private Integer totalCount;

    /**
     * 程序处理时长 单位： 秒
     */
    @TableField("handling_time")
    private Integer handlingTime;

    /**
     * 批量补录状态（0 - BATCH_APPROVED 批量订单核准订单；80 - BATCH_COMPLETED 批量订单完成订单；99 - BATCH_CANCELLED 批量订单取消订单；）  
     */
    @TableField("status_id")
    private Integer statusId;

    /**
     * 创建日期
     */
    @TableField("gmt_date")
    private LocalDate gmtDate;
    
    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间
     */
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;

    @Override
	public String toString() {
		return new Gson().toJson(this);
	}	
}
