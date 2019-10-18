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
 * 订单支付表
 * </p>
 *
 * @author zsy
 * @since 2019-03-04
 */
@Data
@Accessors(chain = true)
public class OrderPay implements Serializable {

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
     * 用户签约协议号（作用于银行卡支付,现批扣使用）
     */
    @TableField("agreementno")
    private String agreementno;

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
     * 合并支付子项个数
     */
    @TableField("pay_details_count")
    private Integer payDetailsCount;

    /**
     * 支付方式（1-在线支付；2-货到付款；3-委托代扣；4-线下补录；5-无需付款；6-贷款支付；7-赊销 ；8-盒子支付；9-管理支付；     金额为0时记为 1-在线支付）
     */
    @TableField("payment_method")
    private Integer paymentMethod;

    /**
     * 商户编号(产品系统获取  -> 上送支付平台 请求头中的 merchantid 商户号)通过产品组接口获取特别注意合并支付 订单支付表该值为空
     */
    @TableField("merchant_id")
    private String merchantId;

    /**
     * 项目编码(产品系统获取 -> 上送支付平台 请求体中的 itemid 项目编号)通过产品组接口获取特别注意合并支付 订单支付表该值为空
     */
    @TableField("item_id")
    private String itemId;

    /**
     * 银行账户ID—>通过产品组接口获取特别注意合并支付 订单支付表该值为空"
     */
    @TableField("bank_account_id")
    private Integer bankAccountId;

    /**
     * 订单金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 交易人-用户id -> 上送支付平台 请求体中 uid 用户在平台唯一标识号
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
     * 报告返回时间
     */
    @TableField("gmt_report")
    private LocalDateTime gmtReport;

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
