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
 * 批扣订单子项表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderDeductionsItem implements Serializable {

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
     * 订单号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 业务批次编号（没有批扣是没有批次概念）
     */
    @TableField("batch_id")
    private Long batchId;

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
     * 业务流水号（账单：账单系统生成的唯一流水 ；   充值缴费：充值缴费流水；  转账：转账流水；  产品（商品）：系统流水号；  退费：原交易流水号  ）
     */
    @TableField("business_no")
    private String businessNo;

    /**
     * 站点编号
     */
    @TableField("site_info_id")
    private Integer siteInfoId;

    /**
     * 产品信息-产品编号（产品订单类：SKU标识码；   人员账单：人员编号；   商户账单：商户标识码；   其他账单：业务编号；   充值缴费类：充值缴费号码）
     */
    @TableField("prod_prod_code")
    private String prodProdCode;

    /**
     * 产品信息-产品名称
     */
    @TableField("prod_prod_name")
    private String prodProdName;

    /**
     * 产品信息-图片信息
     */
    @TableField("prod_image_url")
    private String prodImageUrl;

    /**
     * 管理结算信息-项目编码
     */
    @TableField("setmanag_item_id")
    private Integer setmanagItemId;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 报名联系人状态（   0-已完成；1-待修改  ；）
     */
    @TableField("contact_person_status")
    private Integer contactPersonStatus;

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
     * 客户业务主体-客户联系电话
     */
    @TableField("cust_user_mobile")
    private String custUserMobile;

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
     * 保留小计（按交易金额比例折算）
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 订单状态 （1 - ITEM_REVIEW待商户审核；2 - ITEM_NOPAY待支付；10 - ITEM_RUSHING 订单冲正中；80 - ITEM_COMPLETED完成；99 - ITEM_CANCELLED取消；）
     */
    @TableField("status_id")
    private Integer statusId;

    /**
     * 物流标记 （0 - 无（不需要物流）LOGISTICS_NULL_MARK 1 - 物流  - LOGISTICS_EXIST_MARK 2 - 自提  - LOGISTICS_INVITE_MARK 取自sku信息表中物流标记字段；）
     */
    @TableField("logistics_mark")
    private Integer logisticsMark;

    /**
     * 物流状态 （0 - 不需要物流 LOGISTICS_NULL ；1 - 未发货（待发货）LOGISTICS_WAIT_SHIPMENTS ；2 - 已发货（待收货） LOGISTICS_WAIT_RECEIVE ；3 - 已收货 LOGISTICS_YET_RECEIVE（商户发物流用）；）
     */
    @TableField("logistics_status")
    private Integer logisticsStatus;

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
     * 是否能开票 （0 - 否；1 - 用户无需申请系统自动开票；2 - 用户申请系统自动开票；3 - 用户申请商户审核开票；4 - 用户无需申请商户开票）
     */
    @TableField("invoice_whether_may")
    private Integer invoiceWhetherMay;

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
     * 开票状态  （0 - 未开票；1 - 开票中 ；  2 - 已开票  ； 3 - 退票中；）
     */
    @TableField("invoice_status")
    private Integer invoiceStatus;

    /**
     * 产品退费标识  ，是否允许退费  （0 不允许 ；1 用户和商户都允许 ；  2 仅商户允许；）
     */
    @TableField("prod_refund_status")
    private Integer prodRefundStatus;

    /**
     * 是否允许退费  （0 - 允许，1 - 不允许）
     */
    @TableField("is_allow_refund")
    private Integer isAllowRefund;

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
