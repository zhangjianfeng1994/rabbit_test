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
 * 订单退款进度索引表
 * </p>
 *
 * @author zsy
 * @since 2019-03-04
 */
@Data
@Accessors(chain = true)
public class OrderRefundPlanIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 订单退款进度表主键
     */
    @TableField("refund_plan_id")
    private Long refundPlanId;

    /**
     * 退费订单号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;
    
    /**
     * 受理退款失败描述（支付平台返回 or 平台原因） 
     */
    @TableField("result_message")
    private String resultMessage;

    /**
     * 订单状态（0 - PAYMENT_UNKNOW-结果未知；80 - PAYMENT_SETTLED-支付已清算；99 - PAYMENT_CANCELLED-支付已取消；）
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
