package com.sltas.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.FutureTask;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.gson.Gson;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 退费订单对账临时表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderRefundAccountTemp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 交易日期
     */
    @TableField("order_date")
    private String orderDate;

    /**
     * 交易类型
     */
    @TableField("order_type")
    private String orderType;

    /**
     * 退款支付成功时间
     */
    @TableField("refund_pay_time")
    private LocalDateTime refundPayTime;

    /**
     * 商户退款订单号
     */
    @TableField("refund_trans_no")
    private String refundTransNo;

    /**
     * 退款金额
     */
    @TableField("refund_amount")
    private BigDecimal refundAmount;

    /**
     * 退款实际支付金额
     */
    @TableField("refund_pay_amount")
    private BigDecimal refundPayAmount;

    /**
     * 退款渠道流水号
     */
    @TableField("refund_id")
    private Long refundId;
    
    /**
	 * 交易人-用户id
	 */
	@TableField("tran_user_id")
	private Integer tranUserId;

    /**
     * 退款商户承担手续费(元)
     */
    @TableField("refund_merch_fee")
    private BigDecimal refundMerchFee;

    /**
     * 退款个人承担手续费(元)
     */
    @TableField("refund_custom_fee")
    private BigDecimal refundCustomFee;

    /**
     * 退款支付通道标识
     */
    @TableField("refund_pathno")
    private String refundPathno;

    /**
     * 退款订单状态
     */
    @TableField("refund_status_id")
    private String refundStatusId;

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
     * 原商户支付订单号
     */
    @TableField("old_pay_id")
    private Long oldPayId;

    /**
     * 原商户订单金额(元)
     */
    @TableField("old_amount")
    private BigDecimal oldAmount;

    /**
     * 退还商户手续费
     */
    @TableField("return_merch_fee")
    private BigDecimal returnMerchFee;

    /**
     * 商户费用结算方式  （01-后结算；02-预充值(充值账号扣除)； 03-实时结算）
     */
    @TableField("fee_settle_type")
    private String feeSettleType;

    /**
     * 退款资金来源 （ order_fee(订单未结算资金退款) ； account_fee(账户资金退款)）
     */
    @TableField("refund_account")
    private String refundAccount;

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
    
    
    /***********************************自定义基础值********************************************/	

	/**
	 * 基础操作类 追溯结果
	 */
	private transient FutureTask<String> futureTask;

    @Override
	public String toString() {
		return new Gson().toJson(this);
	}	
}
