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
 * 订单退款表
 * </p>
 *
 * @author zsy
 * @since 2019-03-20
 */
@Data
@Accessors(chain = true)
public class OrderRefund implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 退费订单流水号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 业务类型（1 - BILLS_ORDER账单订单；2 - SALES_ORDER销售订单；3 - RECHARGE_ORDER充值订单；4 - TRANSFER_ORDER转账订单；）
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 外部系统商户流水号
     */
    @TableField("out_sl_merchant_no")
    private String outSlMerchantNo;

    /**
     * 支付订单子项号
     */
    @TableField("old_item_no")
    private String oldItemNo;

    /**
     * 支付订单号
     */
    @TableField("old_trans_no")
    private String oldTransNo;

    /**
     * 业务流水号
     */
    @TableField("old_business_no")
    private String oldBusinessNo;

    /**
     * 支付流水号
     */
    @TableField("old_pay_id")
    private Long oldPayId;

    /**
     * 原订单商联内部系统调用流水号
     */
    @TableField("old_third_party_no")
    private String oldThirdPartyNo;

    /**
     * 退款来源1.用户发起2.商户发起
     */
    @TableField("refund_source")
    private Integer refundSource;

    /**
     * 操作人id
     */
    @TableField("oper_user_id")
    private Integer operUserId;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 原订单子项保留小计金额
     */
    @TableField("old_amount")
    private BigDecimal oldAmount;

    /**
     * 数量（账单、充值缴费类默认1； 商品类购买的数量或退的数量）
     */
    @TableField("old_quantity")
    private Integer oldQuantity;

    /**
     * 折扣价
     */
    @TableField("old_discount_price")
    private BigDecimal oldDiscountPrice;

    /**
     * 产品信息-产品编号（产品订单类：SKU标识码；  人员账单：人员编号；  商户账单：商户标识码；  其他账单：业务编号；  充值缴费类：充值缴费号码)；）
     */
    @TableField("old_prod_prod_code")
    private String oldProdProdCode;

    /**
     * 产品信息-产品名称
     */
    @TableField("old_prod_prod_name")
    private String oldProdProdName;

    /**
     * 产品信息-图片信息
     */
    @TableField("old_prod_image_url")
    private String oldProdImageUrl;

    /**
     * 站点编号
     */
    @TableField("site_info_id")
    private Integer siteInfoId;

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
     * 退款资金来源(退款前)，退款前根据产品目编码获得（1 - order_fee(订单未结算资金退款)；0 - account_fee(账户资金退款)；）
     */
    @TableField("refund_account_before")
    private String refundAccountBefore;

    /**
     * 成功退费总金额 
     */
    @TableField("refund_total_amount")
    private BigDecimal refundTotalAmount;
    
    /**
     * 成功退费总数量（物品数量）
     */
    @TableField("refund_total_count")
    private Integer refundTotalCount;

    /**
     * 申请退费金额
     */
    @TableField("refund_apply_amount")
    private BigDecimal refundApplyAmount;

    /**
     * 申请退款数量
     */
    @TableField("refund_apply_count")
    private Integer refundApplyCount;

    /**
     * 审批退款金额
     */
    @TableField("refund_actual_amount")
    private BigDecimal refundActualAmount;

    /**
     * 审批退款数量
     */
    @TableField("refund_actual_count")
    private Integer refundActualCount;

    /**
     * 退款方式   （0 原路径；1 线下；）
     */
    @TableField("refund_type")
    private Integer refundType;

    /**
     * 退款支付渠道  （1 数字王府(银联)；3 通联支付；5 京东快捷支付；6 微信支付(公众号)；7 一网通支付；8 支付宝支付；9 银联在线支付；10 微信支付（服务商）；2001 中金单笔代收；11 银联全渠道；12 山东工行B2C；13 青岛工行B2C；14 浙江工行B2C；15 沈阳工行B2C；16 微信支付（兴业银行）；18 农行支付；19 银行支付（银企汇）；150001 现金；150002 POS；150003 转账；150004 贷款；150005 抵扣；150006 减免；150007 免费（0元支付） ；150009 批扣；150010 微信支付(线下)；150011 支付宝支付(线下)；151000 其它；）
     */
    @TableField("refund_channel")
    private String refundChannel;

    /**
     * 退款管理表主键
     */
    @TableField("order_refund_manage_id")
    private Long orderRefundManageId;

    /**
     * 退款订单支付流水号-> (请求退款head参数  channelserialno )退款订单最后成功退费流水号
     */
    @TableField("refund_id")
    private Long refundId;

    /**
     * 订单退费时间
     */
    @TableField("refund_time")
    private LocalDateTime refundTime;

    /**
     * 订单状态（0 - ORDER_REFUNDACCEPTED退款受理中；1 - ORDER_REFUND退款中；80 - ORDER_COMPLETED完成订单；99 - ORDER_CANCELLED取消订单)；）
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

    /**
     * 最后一次调起退费时间
     */
    @TableField("last_refund_time")
    private LocalDateTime lastRefundTime;

    @Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
