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
 * 批扣订单子项详情表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderDeductionsItemDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 订单子项号
     */
    @TableField("item_no")
    private String itemNo;

    /**
     * 审核间隔时间（分钟）
     */
    @TableField("review_interval_time")
    private Integer reviewIntervalTime;

    /**
     * 审核超时时间
     */
    @TableField("review_time_limit")
    private LocalDateTime reviewTimeLimit;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 产品信息-产品类型（01商品类；02报名服务；03选课培训；04收费类；05场馆预订（不动产租赁）；06宾馆预订（不动产租赁）；07其他（动产租赁）；08考试；20单一账单-人（目前人员账单）；21单一账单-其他；22多元账单-人；23多元账单-其他；24充值；25转账；26 捐赠；）
     */
    @TableField("prod_prod_type")
    private String prodProdType;

    /**
     * 租赁时间单位类型(null 宾馆预订、场地预定等租赁类填写（1 - 按时； 2 - 按日；3 - 按月；4 - 按年；）
     */
    @TableField("lease_time_type")
    private Integer leaseTimeType;

    /**
     * 租赁起始日时间（Null 宾馆预订、场地预定等租赁类填写 用户预订的日期 合同计价起始时间）
     */
    @TableField("lease_time_start")
    private LocalDateTime leaseTimeStart;

    /**
     * 时间单位数量 （Null 宾馆预订、场地预定等租赁类填写）
     */
    @TableField("time_unit")
    private Integer timeUnit;

    /**
     * 数量（账单、充值缴费类默认1； 商品类购买的数量或退的数量）
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 单价
     */
    @TableField("unit_price")
    private BigDecimal unitPrice;

    /**
     * 折扣价
     */
    @TableField("discount_price")
    private BigDecimal discountPrice;

    /**
     * 下单金额(=数量*折扣价)
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 人工调整金额（在下单金额基础上增或减的金额，以符号+/- 表示增减）
     */
    @TableField("change_amount")
    private BigDecimal changeAmount;

    /**
     * 调价说明
     */
    @TableField("change_amount_explain")
    private String changeAmountExplain;

    /**
     * 交易金额（下单金额+/-人工调整金额）
     */
    @TableField("trans_amount")
    private BigDecimal transAmount;

    /**
     * 抵用金额 
     */
    @TableField("voucher_amount")
    private BigDecimal voucherAmount;

    /**
     * 保留小计（按交易金额比例折算）
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 操作人-审核人id
     */
    @TableField("oper_reuser_id")
    private Integer operReuserId;

    /**
     * 操作人-审核意见 -> 对应订单审核
     */
    @TableField("oper_review_opinion")
    private String operReviewOpinion;

    /**
     * 操作人-审核时间
     */
    @TableField("oper_review_time")
    private LocalDateTime operReviewTime;

    /**
     * 支付后类型（0 - 普通支付；1 - 分账支付；）
     */
    @TableField("after_pay_type")
    private Integer afterPayType;

    /**
     * 订单支付时间
     */
    @TableField("gmt_pay")
    private LocalDateTime gmtPay;

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
     * 商户到账金额(元)  (退款对应实际退款金额)
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
     * 设备编号
     */
    @TableField("device_info")
    private String deviceInfo;

    /**
     * 通道商户订单号（支付平台调用行方,正向仅记录）
     */
    @TableField("sl_bank_no")
    private String slBankNo;

    /**
     * 交易人-留言
     */
    @TableField("tran_remark")
    private String tranRemark;

    /**
     * 交易人-附言
     */
    @TableField("tran_postscript")
    private String tranPostscript;

    /**
     * 账单说明
     */
    @TableField("bill_instructions")
    private String billInstructions;

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
