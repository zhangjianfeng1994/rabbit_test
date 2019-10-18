package com.sltas.order.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.gson.Gson;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 对账统计表   
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderAccountStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 对账类型  ( 1 -  单笔对账；2 - 批扣对账；3 - 退费对账；）
     */
    @TableField("account_type")
    private Integer accountType;

    /**
     *  对账日期（YYYYMMdd）
     */
    @TableField("account_date")
    private String accountDate;

    /**
     * 订单交易日更新前成功笔数
     */
    @TableField("order_before_count")
    private Integer orderBeforeCount;

    /**
     * 订单交易日更新后成功笔数
     */
    @TableField("order_after_count")
    private Integer orderAfterCount;

    /**
     * 对账成功笔数（支付平台对账文件）
     */
    @TableField("account_succ_count")
    private Integer accountSuccCount;

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
