package com.sltas.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.gson.Gson;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 批扣订单表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderDeductionsHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 订单号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 业务批次编号
     */
    @TableField("batch_id")
    private Long batchId;

    /**
     * 业务模式（1 - B2C；2 - B2B）
     */
    @TableField("order_model")
    private Integer orderModel;

    /**
     * 业务类型（1 - BILLS_ORDER账单订单；2 - SALES_ORDER销售订单；3 - RECHARGE_ORDER充值订单；4 - TRANSFER_ORDER转账订单；）
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 支付前类型（0 - 普通支付；1 - 合并支付；）
     */
    @TableField("before_pay_type")
    private Integer beforePayType;

    /**
     * 渠道请求源（0 - 商联系统调用 （内部系统发起例如退费失败会在mer端可以继续出纳）；1 - 其他第三方系统调用（外部系统发起api方式不论成功还是失败都会递送报告）；）
     */
    @TableField("channel_source")
    private Integer channelSource;

    /**
     * 商联内部系统调用流水号
     */
    @TableField("third_party_no")
    private String thirdPartyNo;

    /**
     * 设备编号
     */
    @TableField("device_info")
    private String deviceInfo;

    /**
     * 工具平台业务流水号(盒子发起退款因没有订单这面的请求订单及流水，只能通过支付平台流水号进行退费操作)
     */
    @TableField("sl_busi_id")
    private String slBusiId;

    /**
     * 通道商户订单号（支付平台调用行方,正向仅记录）
     */
    @TableField("sl_bank_no")
    private String slBankNo;

    /**
     * 站点编号
     */
    @TableField("site_info_id")
    private Integer siteInfoId;

    /**
     * 订单超时间隔时间（分钟）
     */
    @TableField("pay_interval_time")
    private Integer payIntervalTime;

    /**
     * 订单超时时间
     */
    @TableField("pay_time_limit")
    private LocalDateTime payTimeLimit;

    /**
     * 支付方式（1-在线支付；2-货到付款；3-委托代扣；4-线下补录；5-无需付款；6-贷款支付；7-赊销 ；8-盒子支付；9-管理支付；     金额为0时记为 1-在线支付）
     */
    @TableField("payment_method")
    private Integer paymentMethod;

    /**
     * 支付分类 （0 - 线上；1 - 线下；）
     */
    @TableField("payment_method_type")
    private Integer paymentMethodType;

    /**
     * 支付渠道  （1 数字王府(银联)；3 通联支付；5 京东快捷支付；6 微信支付(公众号)；7 一网通支付；8 支付宝支付；9 银联在线支付；10 微信支付（服务商）；2001 中金单笔代收；11 银联全渠道；12 山东工行B2C；13 青岛工行B2C；14 浙江工行B2C；15 沈阳工行B2C；16 微信支付（兴业银行）；18 农行支付；19 银行支付（银企汇）；150001 现金；150002 POS；150003 转账；150004 贷款；150005 抵扣；150006 减免；150007 免费（0元支付） ；150009 批扣；150010 微信支付(线下)；150011 支付宝支付(线下)；151000 其它；）
     */
    @TableField("payment_channel")
    private String paymentChannel;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 订单子项的数量总和
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 交易金额（=下单金额+/-人工调整金额）
     */
    @TableField("trans_amount")
    private BigDecimal transAmount;

    /**
     * 抵用金额
     */
    @TableField("voucher_amount")
    private BigDecimal voucherAmount;

    /**
     * 保留小计（=交易金额-未来各积分优惠券抵扣）
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 订单支付号,最后支付成功的订单流水号、主键
     */
    @TableField("pay_id")
    private Long payId;

    /**
     * 订单支付时间
     */
    @TableField("pay_time")
    private LocalDateTime payTime;

    /**
     * 实际支付金额(元)
     */
    @TableField("pay_amount")
    private BigDecimal payAmount;

    /**
     * 商户费用结算方式 （01-后结算；02-预充值(充值账号扣除) ；03-实时结算；）
     */
    @TableField("fee_settle_type")
    private String feeSettleType;

    /**
     * 商户到账金额(元)  （退款对应实际退款金额）
     */
    @TableField("arrival_amount")
    private BigDecimal arrivalAmount;

    /**
     * 分账金额（根据分账方式返回，其他时候为0）
     */
    @TableField("routing_amount")
    private BigDecimal routingAmount;

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
     * 订单状态（0 - ORDER_APPROVED核准订单；1 - ORDER_REVIEW待商户审核；2 - ORDER_NOPAY待支付；10 - ORDER_RUSHING 订单冲正中；80 - ORDER_COMPLETED完成订单；99 - ORDER_CANCELLED取消订单；）
     */
    @TableField("status_id")
    private Integer statusId;

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
