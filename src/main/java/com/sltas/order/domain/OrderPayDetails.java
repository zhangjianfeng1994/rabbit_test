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
 * 订单支付流水详情表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderPayDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

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
     *  工具平台业务流水号（盒子发起退款因没有订单这面的请求订单及流水，只能通过支付平台流水号进行退费操作）
     */
    @TableField("sl_busi_id")
    private String slBusiId;

    /**
     * 商户编号(产品系统获取  -> 上送支付平台 请求头中的 merchantid 商户号)
     */
    @TableField("merchant_id")
    private String merchantId;

    /**
     * 项目编码(产品系统获取 -> 上送支付平台 请求体中的 itemid 项目编号)
     */
    @TableField("item_id")
    private String itemId;

    /**
     * 收费账户ID（产品系统获取）
     */
    @TableField("bank_account_id")
    private Integer bankAccountId;

    /**
     * 订单金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 支付状态（0 - PAYMENT_UNKNOW-结果未知；80 - PAYMENT_SETTLED-支付已清算；99 - PAYMENT_CANCELLED-支付已取消；）
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
