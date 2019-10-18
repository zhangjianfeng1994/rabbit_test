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
 * 订单退款详情表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderRefundDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

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
     * 源业务类型（1 - BILLS_ORDER账单订单；2 - SALES_ORDER销售订单；3 - RECHARGE_ORDER充值订单；4 - TRANSFER_ORDER转账订单；）
     */
    @TableField("old_order_type")
    private Integer oldOrderType;

    /**
     * 支付方式（1-在线支付；2-货到付款；3-委托代扣；4-线下补录；5-无需付款；6-贷款支付；7-赊销 ；8-盒子支付；9-管理支付；     金额为0时记为 1-在线支付）
     */
    @TableField("old_payment_method")
    private Integer oldPaymentMethod;

    /**
     * 源支付分类   （0 - 线上；1 - 线下；）
     */
    @TableField("old_payment_method_type")
    private Integer oldPaymentMethodType;

    /**
     * 支付渠道  （1 数字王府(银联)；3 通联支付；5 京东快捷支付；6 微信支付(公众号)；7 一网通支付；8 支付宝支付；9 银联在线支付；10 微信支付（服务商）；2001 中金单笔代收；11 银联全渠道；12 山东工行B2C；13 青岛工行B2C；14 浙江工行B2C；15 沈阳工行B2C；16 微信支付（兴业银行）；18 农行支付；19 银行支付（银企汇）；150001 现金；150002 POS；150003 转账；150004 贷款；150005 抵扣；150006 减免；150007 免费（0元支付） ；150009 批扣；150010 微信支付(线下)；150011 支付宝支付(线下)；151000 其它；）
     */
    @TableField("old_payment_channel")
    private String oldPaymentChannel;

    /**
     * 外部系统商户流水号
     */
    @TableField("out_sl_merchant_no")
    private String outSlMerchantNo;

    /**
     * 工具平台业务流水号（盒子发起退款因没有订单这面的请求订单及流水，只能通过支付平台流水号进行退费操作）
     */
    @TableField("sl_busi_id")
    private String slBusiId;

    /**
     * 客户业务主体-客户名称
     */
    @TableField("cust_user_name")
    private String custUserName;

    /**
     * 客户业务主体-客户联系电话
     */
    @TableField("cust_user_mobile")
    private String custUserMobile;

    /**
     * 人员编号
     */
    @TableField("cust_ident_code")
    private String custIdentCode;

    /**
     * 原订单子项保留小计金额
     */
    @TableField("old_amount")
    private BigDecimal oldAmount;

    /**
     * 退费申请原因（系统字典配置，用户选择）
     */
    @TableField("refund_cause")
    private Integer refundCause;

    /**
     * 退费申请说明（用户输入）
     */
    @TableField("refund_explain")
    private String refundExplain;

    /**
     * 受理人意见
     */
    @TableField("accept_review_opinion")
    private String acceptReviewOpinion;

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
     * 退还商户手续费 （仅退费有：返回值  退费，正值 )-> (请求退款body参数  refundmerchantfee ）
     */
    @TableField("refund_merchant_fee")
    private BigDecimal refundMerchantFee;

    /**
     * 退款资金来源(退款前)，退款前根据产品目编码获得（1 - order_fee(订单未结算资金退款)；0 - account_fee(账户资金退款)；-> (请求退款body参数  refundaccount )）
     */
    @TableField("refund_account_before")
    private String refundAccountBefore;

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
