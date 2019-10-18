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
 * 退款管理表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderRefundManage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单信息管理表主键
     */
    @TableId("id")
    private Long id;
    
    /**
	     * 退款商联内部系统调用流水号
	 */
    @TableId("third_party_no")
	private String thirdPartyNo;

    /**
     * 退费订单号
     */
    @TableField("trans_no")
    private String transNo;
    
    /**
     * 原支付订单号
     */
    @TableField("old_trans_no")
    private String oldTransNo;

    /**
     * 支付订单子项号
     */
    @TableField("old_item_no")
    private String oldItemNo;

    /**
     * 支付流水号、支付表主键
     */
    @TableField("old_pay_id")
    private Long oldPayId;

    /**
     * 通道商户订单号（支付平台调用行方,正向仅记录）
     */
    @TableField("sl_bank_no")
    private String slBankNo;

    /**
     * 渠道请求源（0 - 商联系统调用 （内部系统发起例如退费失败会在mer端可以继续出纳）；1 - 其他第三方系统调用（外部系统发起api方式不论成功还是失败都会递送报告）；）
     */
    @TableField("channel_source")
    private Integer channelSource;

    /**
     * 业务流水号 （账单：账单系统生成的唯一流水 ；  充值缴费：充值缴费流水；  转账：转账流水；  产品（商品）：系统流水号；  退费：原交易流水号  ）
     */
    @TableField("old_business_no")
    private String oldBusinessNo;

    /**
     * 源支付方式（1-在线支付；2-货到付款；3-委托代扣；4-线下补录；5-无需付款；6-贷款支付；7-赊销 ；8-盒子支付；9-管理支付 ；   注：金额为0时记为 1-在线支付）
            
     */
    @TableField("old_payment_method")
    private Integer oldPaymentMethod;

    /**
     * 源支付分类  （ 0 - 线上；1 - 线下；）
     */
    @TableField("old_payment_method_type")
    private Integer oldPaymentMethodType;

    /**
     * 源支付渠道  （1 数字王府(银联)；3 通联支付；5 京东快捷支付；6 微信支付(公众号)；7 一网通支付；8 支付宝支付；9 银联在线支付；10 微信支付（服务商）；2001 中金单笔代收；11 银联全渠道；12 山东工行B2C；13 青岛工行B2C；14 浙江工行B2C；15 沈阳工行B2C；16 微信支付（兴业银行）；18 农行支付；19 银行支付（银企汇）；150001 现金；150002 POS；150003 转账；150004 贷款；150005 抵扣；150006 减免；150007 免费（0元支付） ；150009 批扣；150010 微信支付(线下)；150011 支付宝支付(线下)；151000 其它；）
     */
    @TableField("old_payment_channel")
    private String oldPaymentChannel;
    
    /**
     * 周期编码
     */
    @TableField("period_code")
    private String periodCode;

    /**
     * 产品信息-产品类型（01商品类；02报名服务；03选课培训；04收费类；05场馆预订（不动产租赁）；06宾馆预订（不动产租赁）；07其他（动产租赁）；08考试；   20单一账单-人（目前人员账单）；21单一账单-其他；22多元账单-人；23多元账单-其他；24充值；25转账；26 捐赠；）
     */
    @TableField("prod_prod_type")
    private String prodProdType;

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
     * 人员编号
     */
    @TableField("cust_ident_code")
    private String custIdentCode;

    /**
     * 客户业务主体-商户id
     */
    @TableField("cust_merch_id")
    private Integer custMerchId;

    /**
     * 退费申请原因（系统字典配置，用户选择）
     */
    @TableField("refund_cause")
    private String refundCause;

    /**
     * 退费申请说明（用户输入）
     */
    @TableField("refund_explain")
    private String refundExplain;

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
     * 受理退款失败描述（支付平台返回 or 平台原因） 
     */
    @TableField("result_message")
    private String resultMessage;
    
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
     *  退款状态（2 - 退款待业务受理；3 - 退款待业务审核；4 - 退款业务审核不通过；5 - 退款待财务受理；6 - 退款财务受理不通过；7 - 退款待财务审核；8 - 退款财务审核不通过；9 - 退款待财务领导审核；10 -退款财务领导审核不通过；11 -退款待财务出纳； 12 - 退款中；13 - 退款出纳失败； 80 - 完成；99 - 取消；）
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
