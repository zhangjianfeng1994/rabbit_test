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
 * 批扣订单支付报告表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderDeductionsPayReport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 订单支付流水号
     */
    @TableField("pay_id")
    private Long payId;

    /**
     * 订单号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 工具平台业务流水号（盒子发起退款因没有订单这面的请求订单及流水，只能通过支付平台流水号进行退费操作）
     */
    @TableField("sl_busi_id")
    private String slBusiId;

    /**
     * 订单支付时间
     */
    @TableField("pay_time")
    private LocalDateTime payTime;

    /**
     * 通道商户订单号（支付平台调用行方,正向仅记录）管理支付业务上送字段 -> (支付返回 pathorderid)原调用通道支付商户支付订单号成功时返回
     */
    @TableField("sl_bank_no")
    private String slBankNo;

    /**
     * 支付渠道  （1 数字王府(银联)；3 通联支付；5 京东快捷支付；6 微信支付(公众号)；7 一网通支付；8 支付宝支付；9 银联在线支付；10 微信支付（服务商）；2001 中金单笔代收；11 银联全渠道；12 山东工行B2C；13 青岛工行B2C；14 浙江工行B2C；15 沈阳工行B2C；16 微信支付（兴业银行）；18 农行支付；19 银行支付（银企汇）；150001 现金；150002 POS；150003 转账；150004 贷款；150005 抵扣；150006 减免；150007 免费（0元支付） ；150009 批扣；150010 微信支付(线下)；150011 支付宝支付(线下)；151000 其它；）
     */
    @TableField("payment_channel")
    private String paymentChannel;

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
     * 商户自定义附言  -> (支付返回 postscript )
     */
    @TableField("post_script")
    private String postScript;

    /**
     * 保留字段  -> (支付返回  reserved)
     */
    @TableField("reserved")
    private String reserved;

    /**
     * 用户签约协议号（作用于银行卡支付,现批扣使用）  -> (支付返回 agreementno )
     */
    @TableField("agreementno")
    private String agreementno;

    /**
     * 账户号码 -> (支付返回 cardid )
     */
    @TableField("card_id")
    private String cardId;

    /**
     * 账户名称 -> (支付返回 name )
     */
    @TableField("card_name")
    private String cardName;

    /**
     * 开户证件类型 -> (支付返回 documenttype )
     */
    @TableField("document_type")
    private String documentType;

    /**
     * 证件号码 -> (支付返回 documentid)
     */
    @TableField("document_id")
    private String documentId;

    /**
     * 银行预留手机号 -> (支付返回 telephone)
     */
    @TableField("telephone")
    private String telephone;

    /**
     * 卡类型 -> (支付返回 cardtype)
     */
    @TableField("card_type")
    private String cardType;

    /**
     * 信用卡有效期 -> (支付返回 creditcardvalidate)
     */
    @TableField("credit_card_validate")
    private String creditCardValidate;

    /**
     * 信用卡背面的末3位数字 -> (支付返回 lastthreenumber)
     */
    @TableField("last_three_number")
    private String lastThreeNumber;

    /**
     * 银行编码 -> (支付返回 bankcode)
     */
    @TableField("bank_code")
    private String bankCode;

    /**
     * 银行名称 -> (支付返回 bankname)
     */
    @TableField("bank_name")
    private String bankName;

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
