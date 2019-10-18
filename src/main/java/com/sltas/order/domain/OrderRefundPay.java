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
 * 订单退款流水表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderRefundPay implements Serializable {

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
     * 外部系统商户流水号
     */
    @TableField("out_sl_merchant_no")
    private String outSlMerchantNo;

    /**
     * 支付订单子项保留小计金额
     */
    @TableField("old_amount")
    private BigDecimal oldAmount;

    /**
     * 退款金额
     */
    @TableField("refund_amount")
    private BigDecimal refundAmount;

    /**
     * 源工具平台业务流水号（盒子发起退款因没有订单这面的请求订单及流水，只能通过支付平台流水号进行退费操作）
     */
    @TableField("old_sl_busi_id")
    private String oldSlBusiId;

    /**
     * 源订单流水号 
     */
    @TableField("old_trans_no")
    private String oldTransNo;

    /**
     * 支付订单子项号
     */
    @TableField("old_item_no")
    private String oldItemNo;

    /**
     * 订单支付号,最后支付成功的订单流水号、主键
     */
    @TableField("old_pay_id")
    private Long oldPayId;

    /**
     * 订单支付流水详情号、主键
     */
    @TableField("old_pay_details_id")
    private Long oldPayDetailsId;

    /**
     * 支付前类型（0 - 普通支付；1 - 合并支付；）
     */
    @TableField("old_before_pay_type")
    private Integer oldBeforePayType;

    /**
     * 源支付渠道  （1 数字王府(银联)；3 通联支付；5 京东快捷支付；6 微信支付(公众号)；7 一网通支付；8 支付宝支付；9 银联在线支付；10 微信支付（服务商）；2001 中金单笔代收；11 银联全渠道；12 山东工行B2C；13 青岛工行B2C；14 浙江工行B2C；15 沈阳工行B2C；16 微信支付（兴业银行）；18 农行支付；19 银行支付（银企汇）；150001 现金；150002 POS；150003 转账；150004 贷款；150005 抵扣；150006 减免；150007 免费（0元支付） ；150009 批扣；150010 微信支付(线下)；150011 支付宝支付(线下)；151000 其它；）
     */
    @TableField("old_payment_method")
    private Integer oldPaymentMethod;

    /**
     * 退款资金来源(退款前)，退款前根据产品目编码获得1 - order_fee(订单未结算资金退款)；0 - account_fee(账户资金退款)
     */
    @TableField("refund_account_before")
    private String refundAccountBefore;
    
    /**
     * 商户编号(产品系统获取  -> 上送支付平台 请求头中的 merchantid 商户号)通过产品组接口获取特别注意合并支付 订单支付表该值为空
     */
    @TableField("merchant_id")
    private String merchantId;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 工具平台业务流水号（盒子发起退款因没有订单这面的请求订单及流水，只能通过支付平台流水号进行退费操作）
     */
    @TableField("sl_busi_id")
    private String slBusiId;

    /**
     * 是否计费（0-代表不计费；1-代表计费；）
     */
    @TableField("paid")
    private String paid;

    /**
     * 商联响应时间
     */
    @TableField("response_time")
    private String responseTime;

    /**
     * 处理结果类型
     */
    @TableField("result_type")
    private String resultType;

    /**
     * 订单支付状态
     */
    @TableField("body_status")
    private String bodyStatus;

    /**
     * 处理结果编码
     */
    @TableField("result_code")
    private String resultCode;

    /**
     * 处理结果
     */
    @TableField("result_msg")
    private String resultMsg;

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
