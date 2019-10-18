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
 * 订单对账临时表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderAccountTemp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 对账日期
     */
    @TableField("order_date")
    private String orderDate;

    /**
     * 交易类型
     */
    @TableField("order_type")
    private String orderType;

    /**
     * 支付成功时间
     */
    @TableField("pay_time")
    private LocalDateTime payTime;

    /**
     * 商户号
     */
    @TableField("merchant_id")
    private String merchantId;

    /**
     * 商户项目编号
     */
    @TableField("item_id")
    private String itemId;

    /**
     * 商户订单号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 支付平台业务流水（商联流水号）
     */
    @TableField("slbusiid")
    private String slbusiid;
    
    /**
     * 支付平台业务流水（商联流水号）
     */
    @TableField("pay_no")
    private String payNo;
    
    /**
	 * 交易人-用户id
	 */
	@TableField("tran_user_id")
	private Integer tranUserId;

    /**
     * 商户订单金额(元)
     */
    @TableField("order_fee")
    private BigDecimal orderFee;

    /**
     * 实际支付金额(元)
     */
    @TableField("pay_fee")
    private BigDecimal payFee;

    /**
     * 商户到账金额(元)
     */
    @TableField("merch_amount")
    private BigDecimal merchAmount;
    
    /**
     * 分账金额（根据分账方式返回，其他时候为0）
     */
    @TableField("routing_amount")
    private BigDecimal routingAmount;

    /**
     * 商户承担手续费(元)
     */
    @TableField("merch_fee")
    private BigDecimal merchFee;

    /**
     * 个人承担手续费(元)
     */
    @TableField("custom_fee")
    private BigDecimal customFee;

    /**
     * 银行承担手续费(元)
     */
    @TableField("bank_fee")
    private BigDecimal bankFee;

    /**
     * 其他承担手续费(元)
     */
    @TableField("other_fee")
    private BigDecimal otherFee;

    /**
     * 支付通道标识
     */
    @TableField("pathno")
    private String pathno;
    
    /**
     * 渠道流水号（普通支付时为订单支付流水号；合并支付时为订单支付流水详情号）
     */
    @TableField("pay_id")
    private Long payId;
    
    /**
     * 订单流水号（合并支付时使用，为订单支付总流水号）
     */
    @TableField("order_pay_id")
    private Long orderPayId;
    

    /**
     * 客户绑卡协议号
     */
    @TableField("protocol_id")
    private String protocolId;

    /**
     * 订单状态
     */
    @TableField("status_id")
    private String statusId;

    /**
     * 返回码
     */
    @TableField("result_code")
    private String resultCode;

    /**
     * 返回码信息
     */
    @TableField("result_msg")
    private String resultMsg;

    /**
     * 商户费用结算方式  （01-后结算；02-预充值(充值账号扣除)； 03-实时结算）
     */
    @TableField("fee_settle_type")
    private String feeSettleType;

    /**
     * 业务类型  （01-业务类型默认 ； 02-扫码盒子支付； 03-管理支付 ；04-合并支付）
     */
    @TableField("payment_type")
    private String paymentType;

    /**
     * 调用通道支付商户订单号
     */
    @TableField("sl_bank_no")
    private String slBankNo;

    /**
     * 批次号（商户）
     */
    @TableField("batch_id")
    private Long batchId;

    /**
     * 对账逻辑变更标识 （ 1-无需变更 ；2-需要变更；）
     */
    @TableField("is_updates")
    private String isUpdates;

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
