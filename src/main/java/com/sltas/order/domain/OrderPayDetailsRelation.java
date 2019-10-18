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
 * 订单支付详情关系表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderPayDetailsRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 订单支付流水详情号
     */
    @TableField("pay_details_id")
    private Long payDetailsId;

    /**
     * 订单支付流水号
     */
    @TableField("pay_id")
    private Long payId;

    /**
     * 订单号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 订单子项号
     */
    @TableField("item_no")
    private String itemNo;

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

    @Override
	public String toString() {
		return new Gson().toJson(this);
	}	
}
