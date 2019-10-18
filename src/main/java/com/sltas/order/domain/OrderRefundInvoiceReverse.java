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
 * 退票冲正表
 * </p>
 *
 * @author zsy
 * @since 2019-03-04
 */
@Data
@Accessors(chain = true)
public class OrderRefundInvoiceReverse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 冲正的支付订单号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 发票总数  (订单子项个数涉及到的发票数量)冲正业务参数,冲正时不为空
     */
    @TableField("invoice_sum")
    private Integer invoiceSum;

    /**
     * 成功发票数  (返回报告结果执行成功条数)冲正业务参数,冲正时不为空
     */
    @TableField("refund_succ_sum")
    private Integer refundSuccSum;

    /**
     * 失败发票数   (返回报告结果执行失败条数)冲正业务参数,冲正时不为空
     */
    @TableField("refund_fail_sum")
    private Integer refundFailSum;

    /**
     * 剩余未知数   (剩余没有返回报告的执行条数)冲正业务参数,冲正时不为空
     */
    @TableField("refund_unknown_sum")
    private Integer refundUnknownSum;

    /**
     * 退票批次状态（0 - 退票未完成；80.- 退票已全部完成；99 - 退票异常，部分退票失败；）
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
