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
 * 开票表
 * </p>
 *
 * @author zsy
 * @since 2019-03-04
 */
@Data
@Accessors(chain = true)
public class OrderItemInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 开票批次号、主键
     */
    @TableField("order_invoice_batch_id")
    private Long orderInvoiceBatchId;

    /**
     * 支付订单号  (订单主键)
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 支付订单子项号 (订单子项主键)
     */
    @TableField("item_no")
    private String itemNo;

    /**
     * 业务流水号（账单：账单系统生成的唯一流水 ； 充值缴费：充值缴费流水；转账：转账流水；产品（商品）：系统流水号；退费：原交易流水号  ；）
     */
    @TableField("business_no")
    private String businessNo;

    /**
     * 业务类型 （1.退票续开；  2.支付完成开票  -> 0 - 订单系统主动开票；3.发票平台申请  -> 1 - 发票平台申请开票；）
     */
    @TableField("business_type")
    private Integer businessType;

    /**
     * 支付渠道  （1 数字王府(银联)；3 通联支付；5 京东快捷支付；6 微信支付(公众号)；7 一网通支付；8 支付宝支付；9 银联在线支付；10 微信支付（服务商）；2001 中金单笔代收；11 银联全渠道；12 山东工行B2C；13 青岛工行B2C；14 浙江工行B2C；15 沈阳工行B2C；16 微信支付（兴业银行）；18 农行支付；19 银行支付（银企汇）；150001 现金；150002 POS；150003 转账；150004 贷款；150005 抵扣；150006 减免；150007 免费（0元支付） ；150009 批扣；150010 微信支付(线下)；150011 支付宝支付(线下)；151000 其它；）
     */
    @TableField("payment_channel")
    private String paymentChannel;

    /**
     * 可退金额
     */
    @TableField("allow_refund_max_amount")
    private BigDecimal allowRefundMaxAmount;

    /**
     * 可退数量
     */
    @TableField("allow_refund_max_count")
    private Integer allowRefundMaxCount;

    /**
     * 产品名称
     */
    @TableField("prod_prod_name")
    private String prodProdName;

    /**
     * 费用项目编码
     */
    @TableField("setmanag_item_id")
    private Integer setmanagItemId;

    /**
     * 真实客户主体id
     */
    @TableField("read_cust_user_id")
    private Integer readCustUserId;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 交易人-组织机构id
     */
    @TableField("tran_organ_id")
    private Integer tranOrganId;

    /**
     * 处理结果类型（S-业务受理成功 ；F-业务受理失败（技术类校验失败）；）
     */
    @TableField("result_type")
    private String resultType;

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
     * 发票状态 （ 0 - 核准状态；80 - 开票成功；99 - 开票失败；）
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
