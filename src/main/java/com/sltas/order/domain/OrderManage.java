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
 * 订单信息管理表
 * </p>
 *
 * @author zsy
 * @since 2019-03-29
 */
@Data
@Accessors(chain = true)
public class OrderManage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 支付订单子项号
     */
    @TableField("item_no")
    private String itemNo;
    
    /**
     * 业务流水号（账单：账单系统生成的唯一流水 ；   充值缴费：充值缴费流水；  转账：转账流水；  产品（商品）：系统流水号；  退费：原交易流水号  ）
     */
    @TableField("business_no")
    private String businessNo;

    /**
     * 支付订单号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 支付流水号,支付表主键
     */
    @TableField("pay_id")
    private Long payId;

    /**
     * 支付渠道  （1 数字王府(银联)；3 通联支付；5 京东快捷支付；6 微信支付(公众号)；7 一网通支付；8 支付宝支付；9 银联在线支付；10 微信支付（服务商）；2001 中金单笔代收；11 银联全渠道；12 山东工行B2C；13 青岛工行B2C；14 浙江工行B2C；15 沈阳工行B2C；16 微信支付（兴业银行）；18 农行支付；19 银行支付（银企汇）；150001 现金；150002 POS；150003 转账；150004 贷款；150005 抵扣；150006 减免；150007 免费（0元支付） ；150009 批扣；150010 微信支付(线下)；150011 支付宝支付(线下)；151000 其它；）
     */
    @TableField("payment_channel")
    private String paymentChannel;

    /**
     * 工具平台业务流水号(盒子发起退款因没有订单这面的请求订单及流水，只能通过支付平台流水号进行退费操作)
     */
    @TableField("sl_busi_id")
    private String slBusiId;

    /**
     * 通道商户订单号（支付平台调用行方,正向仅记录,管理支付业务上送字段,为了方便发起退款时候核对微信中的订单号）
     */
    @TableField("sl_bank_no")
    private String slBankNo;

    /**
     * 业务类型（1 - BILLS_ORDER账单订单；2 - SALES_ORDER销售订单；3 - RECHARGE_ORDER充值订单；4 - TRANSFER_ORDER转账订单；）
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 支付后类型 （0 - 普通支付；1 - 合并支付）
     */
    @TableField("before_pay_type")
    private Integer beforePayType;

    /**
     * 支付后类型（0 - 普通支付；1 - 分账支付）
     */
    @TableField("after_pay_type")
    private Integer afterPayType;

    /**
     * 真实客户主体id
     */
    @TableField("read_cust_user_id")
    private Integer readCustUserId;

    /**
     * 客户业务主体-客户名称
     */
    @TableField("cust_user_name")
    private String custUserName;

    /**
     * 客户业务主体-商户id
     */
    @TableField("cust_merch_id")
    private Integer custMerchId;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 交易人-用户名称
     */
    @TableField("tran_user_name")
    private String tranUserName;

    /**
     * 交易人-组织机构id
     */
    @TableField("tran_organ_id")
    private Integer tranOrganId;

    /**
     * 交易人-组织机构名称
     */
    @TableField("tran_organ_name")
    private String tranOrganName;

    /**
     * 交易人-联系电话
     */
    @TableField("tran_user_mobile")
    private String tranUserMobile;

    /**
     * 客户业务主体-客户联系电话
     */
    @TableField("cust_user_mobile")
    private String custUserMobile;
    
    /**
     * 产品退费标识（0 不允许 ；1 用户和商户都允许 ； 2 仅商户允许）
     */
    @TableField("prod_refund_status")
    private Integer prodRefundStatus;

    /**
     * 产品信息-产品编号（产品订单类：SKU标识码；   人员账单：人员编号；  商户账单：商户标识码；  其他账单：业务编号；   充值缴费类：充值缴费号码）
     */
    @TableField("prod_prod_code")
    private String prodProdCode;

    /**
     * 产品信息-产品名称
     */
    @TableField("prod_prod_name")
    private String prodProdName;

    /**
     * 产品信息-产品类型（01商品类；02报名服务；03选课培训；04收费类；05场馆预订（不动产租赁）；06宾馆预订（不动产租赁）；07其他（动产租赁）；08考试；   20单一账单-人（目前人员账单）；21单一账单-其他；22多元账单-人；23多元账单-其他；24充值；25转账；26 捐赠；）
     */
    @TableField("prod_prod_type")
    private String prodProdType;

    /**
     * 当前管理商户
     */
    @TableField("manage_merch_id")
    private Integer manageMerchId;

    /**
     * 当前管理机构
     */
    @TableField("manage_organ_id")
    private Integer manageOrganId;

    /**
     * 产品资金结算方-商户id
     */
    @TableField("settle_merch_id")
    private Integer settleMerchId;

    /**
     * 管理结算信息-项目编码
     */
    @TableField("setmanag_item_id")
    private Integer setmanagItemId;
    
    /**
     * 产品业务受理方-商户id
     */
    @TableField("accept_merch_id")
    private Integer acceptMerchId;

    /**
     * 产品业务受理方-组织机构id
     */
    @TableField("accept_organ_id")
    private Integer acceptOrganId;

    /**
     * 产品提供方机构全称
     */
    @TableField("provid_organ_name")
    private String providOrganName;
    
    /**
     * 产品审核机构名称
     */
    @TableField("oper_review_organ_name")
    private String operReviewOrganName;

    /**
     * 数量（账单、充值缴费类默认1； 商品类购买的数量或退的数量）
     */
    @TableField("quantity")
    private Integer quantity;

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
     * 人工调整金额(在下单金额基础上增或减的金额，以符号+/- 表示增减）
     */
    @TableField("change_amount")
    private BigDecimal changeAmount;

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
     * 订单支付时间
     */
    @TableField("pay_time")
    private LocalDateTime payTime;
    
    /**
     * 是否允许退费（ 0 - 允许；1 - 不允许）
     */
    @TableField("is_allow_refund")
    private Integer isAllowRefund;

    /**
     * 成功退费次数
     */
    @TableField("refund_quantity")
    private Integer refundQuantity;

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
     * 待退费总金额
     */
    @TableField("advance_refund_total_amount")
    private BigDecimal advanceRefundTotalAmount;

    /**
     * 待退费总数量
     */
    @TableField("advance_refund_total_count")
    private Integer advanceRefundTotalCount;

    /**
     * 允许退费最大金额
     */
    @TableField("allow_refund_max_amount")
    private BigDecimal allowRefundMaxAmount;

    /**
     * 允许退费最大数量
     */
    @TableField("allow_refund_max_count")
    private Integer allowRefundMaxCount;

    /**
     * 是否满足退费条件
            0 - 允许退费
            1 - 不满足条件（
            当前条件筛选：
            1.产品不允许退费 
            2.允许退费金额与数量 均为0）
            按照筛选的优先级进行判定，只要存在当中一项则认证该订单不在满足退费条件
     */
    @TableField("allow_refund_flag")
    private Integer allowRefundFlag;

    /**
     * 周期编码
     */
    @TableField("period_code")
    private String periodCode;

    /**
     * 是否能开票 （0 - 否；1 - 用户无需申请系统自动开票；2 - 用户申请系统自动开票；3 - 用户申请商户审核开票；4 - 用户无需申请商户开票）
     */
    @TableField("invoice_whether_may")
    private Integer invoiceWhetherMay;

    /**
     * 合并开票标示，用户申请限制（0 - 关  ；1 - 开；）
     */
    @TableField("invoice_merge_mark")
    private Integer invoiceMergeMark;

    /**
     * 票据种类 （1001 国家税务发票；2001 财政非税发票；2002 财政往来票据；2003 财政医疗票据；）
     */
    @TableField("invoice_nature")
    private String invoiceNature;

    /**
     * 抬头类型，用户申请限制（1 - 个人和单位 ；  2 - 仅个人   ；3 - 仅单位；）
     */
    @TableField("invoice_header_type")
    private Integer invoiceHeaderType;

    /**
     * 个人抬头限制，用户申请限制（ 0 - 无   ；1 - 固定值''''个人""  ； 2 - 客户姓名（订单子项中业务主体的姓名；）
     */
    @TableField("invoice_header_restrict")
    private Integer invoiceHeaderRestrict;

    /**
     * 开票商户
     */
    @TableField("invoice_merchant_id")
    private Integer invoiceMerchantId;

    /**
     * 开票机构
     */
    @TableField("invoice_mechanism_id")
    private Integer invoiceMechanismId;

    /**
     * 开票时间
     */
    @TableField("invoice_time")
    private LocalDateTime invoiceTime;

    /**
     * 开票头流水
     */
    @TableField("invoice_header_no")
    private String invoiceHeaderNo;

    /**
     * 发票类型  ： 1电子发票   2纸质发票
     */
    @TableField("invoice_type")
    private Integer invoiceType;

    /**
     * 开票状态  （ 0 - 否；   1 - 开票中；   2 - 已开票；   3 - 退票中；）
     */
    @TableField("invoice_status")
    private Integer invoiceStatus;

    /**
     * 物流状态 （0 - 不需要物流 LOGISTICS_NULL ；1 - 未发货（待发货）LOGISTICS_WAIT_SHIPMENTS ；2 - 已发货（待收货） LOGISTICS_WAIT_RECEIVE ；3 - 已收货 LOGISTICS_YET_RECEIVE（商户发物流用）；）
     */
    @TableField("logistics_status")
    private Integer logisticsStatus;

    /**
     * 数据是否有效（0 - 暂停（防止冲正,临时锁定数据有效性）；80 - 有效；99 - 失效；）
     */
    @TableField("status_id")
    private Integer statusId;

    /**
     * 订单下单时间
     */
    @TableField("gmt_order_create")
    private LocalDateTime gmtOrderCreate;

    /**
     * 创建日期
     */
    @TableField("gmt_date")
    private LocalDate gmtDate;

    /**
     * 订单创建日期
     */
    @TableField("gmt_order_date")
    private LocalDate gmtOrderDate;

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
