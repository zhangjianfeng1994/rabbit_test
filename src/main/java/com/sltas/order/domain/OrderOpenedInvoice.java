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
 * 已开发票表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderOpenedInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 开票头流水
     */
    @TableField("invoice_header_no")
    private String invoiceHeaderNo;

    /**
     * 开票时间
     */
    @TableField("invoice_time")
    private LocalDateTime invoiceTime;

    /**
     * 订单子项流水号
     */
    @TableField("invoice_id")
    private Long invoiceId;

    /**
     * 支付订单号  (订单主键)
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 支付订单子项号 (订单子项主键)
     */
    @TableField("item_no")
    private String itemNo;

    /**
     * 可退金额
     */
    @TableField("allow_refund_max_amount")
    private BigDecimal allowRefundMaxAmount;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 交易人-组织机构id
     */
    @TableField("tran_organ_id")
    private Integer tranOrganId;

    /**
     * 发票状态 （80 - 有效发票；99 - 冲红+作废，无效发票；）
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
