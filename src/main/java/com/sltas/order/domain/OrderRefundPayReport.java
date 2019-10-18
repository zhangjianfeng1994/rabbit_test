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
 * 订单退款报告表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderRefundPayReport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 退款订单流水号、主键
     */
    @TableField("refund_pay_id")
    private Long refundPayId;

    /**
     * 退款订单号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 支付订单号
     */
    @TableField("old_trans_no")
    private String oldTransNo;

    /**
     * 支付订单子项保留小计金额
     */
    @TableField("old_amount")
    private BigDecimal oldAmount;

    /**
     * 退款金额
     */
    @TableField("refund_actual_amount")
    private BigDecimal refundActualAmount;

    /**
     * 订单退费时间
     */
    @TableField("refund_time")
    private LocalDateTime refundTime;

    /**
     * 实际支付金额(元) 
     */
    @TableField("refund_amount")
    private BigDecimal refundAmount;

    /**
     * 商户费用结算方式 （01-后结算；02-预充值(充值账号扣除) ；03-实时结算；）
     */
    @TableField("fee_settle_type")
    private String feeSettleType;
    
    /**
     * 商户应到账金额
     */
    @TableField("arrival_amount")
    private BigDecimal arrivalAmount;

    /**
     * 客户承担手续费
     */
    @TableField("custom_fee")
    private BigDecimal customFee;

    /**
     * 商户承担手续费
     */
    @TableField("merch_fee")
    private BigDecimal merchFee;

    /**
     * 银行承担手续费
     */
    @TableField("bank_fee")
    private BigDecimal bankFee;

    /**
     * 其它承担手续费
     */
    @TableField("other_fee")
    private BigDecimal otherFee;

    /**
     * 退还商户手续费 （仅退费有：返回值  退费，正值 ）
     */
    @TableField("refund_merchant_fee")
    private BigDecimal refundMerchantFee;

    /**
     * 退款资金来源(退款前)，退款前根据产品目编码获得1 - order_fee(订单未结算资金退款)；0 - account_fee(账户资金退款)；
     */
    @TableField("refund_account_after")
    private String refundAccountAfter;

    /**
     * 源支付渠道  （1 数字王府(银联)；3 通联支付；5 京东快捷支付；6 微信支付(公众号)；7 一网通支付；8 支付宝支付；9 银联在线支付；10 微信支付（服务商）；2001 中金单笔代收；11 银联全渠道；12 山东工行B2C；13 青岛工行B2C；14 浙江工行B2C；15 沈阳工行B2C；16 微信支付（兴业银行）；18 农行支付；19 银行支付（银企汇）；150001 现金；150002 POS；150003 转账；150004 贷款；150005 抵扣；150006 减免；150007 免费（0元支付） ；150009 批扣；150010 微信支付(线下)；150011 支付宝支付(线下)；151000 其它；）
     */
    @TableField("payment_channel")
    private String paymentChannel;

    /**
     * 未核销余额(订单余额) 
     */
    @TableField("order_balance")
    private BigDecimal orderBalance;

    /**
     * 未核销可用余额(订单可用余额)
     */
    @TableField("order_available_balance")
    private BigDecimal orderAvailableBalance;

    /**
     * 退款成功笔数
     */
    @TableField("refund_count")
    private Integer refundCount;

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
