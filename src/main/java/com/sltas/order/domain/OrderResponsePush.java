package com.sltas.order.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.gson.Gson;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 结果报告推送表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderResponsePush implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 推送报告类型（1. - 单笔订单报告 （1.单笔报告入库 2.单笔对账 5.审批入库 S 审批成功0元支付 --- F 审批失败   6.订单调价 S 订单调价0元完成  8.单笔补录 S 成功  ---  F 失败）     S 成功  ---  F 失败；2. - 批量订单报告 （3.批扣报告入库 4.批扣对账 9 批量补录 S 成功  ---  F 失败）S 批扣该批次可查询  --- F 批扣该批次失败；3. - 退款报告 （7.退款订单）S 订单退款成功 F 订单退款失败； 4. - 冲正(10 冲正报告)S 成功  ---  F 失败）
     */
    @TableField("push_report_type")
    private Integer pushReportType;

    /**
     * 业务类型（1 - BILLS_ORDER账单订单；2 - SALES_ORDER销售订单；3 - RECHARGE_ORDER充值订单；4 - TRANSFER_ORDER转账订单；）
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 业务类型（P - ORDER_PAY: 订单支付 ；R - ORDER_REFUND: 订单退费；）
     */
    @TableField("service_type")
    private String serviceType;

    /**
     * 业务子类型（例如：订单创建-单笔支付 (单)       P_001）
     */
    @TableField("service_subtype")
    private String serviceSubtype;


    /**
     * 前台流水号（单笔为单笔前台流水，批量为批量请求的前台流水号）
     */
    @TableField("third_party_no")
    private String thirdPartyNo;

    /**
     * 批量订单号
     */
    @TableField("batch_id")
    private Long batchId;

    /**
     * 订单号（支付+退款）
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 流水号（支付+退款的流水号支付流水pay_id 退款流水refund_id）
     */
    @TableField("serial_no")
    private String serialNo;
    
    /**
     * 通道商户订单号（支付平台调用行方,正向仅记录）
     */
    @TableField("sl_bank_no_items")
    private String slBankNoItems;

    /**
     * 订单支付完成返回参数
     */
    @TableField("business_items")
    private String businessItems;

    /**
     * 订单支付时间
     */
    @TableField("complete_time")
    private LocalDateTime completeTime;

    /**
     * 支付渠道  （1 数字王府(银联)；3 通联支付；5 京东快捷支付；6 微信支付(公众号)；7 一网通支付；8 支付宝支付；9 银联在线支付；10 微信支付（服务商）；2001 中金单笔代收；11 银联全渠道；12 山东工行B2C；13 青岛工行B2C；14 浙江工行B2C；15 沈阳工行B2C；16 微信支付（兴业银行）；18 农行支付；19 银行支付（银企汇）；150001 现金；150002 POS；150003 转账；150004 贷款；150005 抵扣；150006 减免；150007 免费（0元支付） ；150009 批扣；150010 微信支付(线下)；150011 支付宝支付(线下)；151000 其它；）
     */
    @TableField("complete_channel")
    private String completeChannel;
    
    /**
     * @Fields completeType: 订单完成类型
     * 
     * 允许空值, 正向订单成功时存在（仅正向支付成功后返回参数）
     * 1 - 正常流程完成
     * 2 - 订单由失败变更至成功
     * 
     * @author 周顺宇 
     * @date 2019年4月26日 上午10:31:32 
     */
    @TableField("complete_type")
    private Integer completeType;
    
    /**
     * 原订单商联内部系统调用流水号
     */
    @TableField("old_third_party_no")
    private String oldThirdPartyNo;

    /**
     * 推送报告推送结果
     */
    @TableField("push_type")
    private String pushType;

    /**
     * 推送报告推送结果码
     */
    @TableField("push_code")
    private String pushCode;

    /**
     * 推送报告推送结果信息
     */
    @TableField("push_msg")
    private String pushMsg;

    /**
     * 推送报告返回结果类型
     */
    @TableField("response_type")
    private String responseType;

    /**
     * 推送报告返回结果码
     */
    @TableField("response_code")
    private String responseCode;

    /**
     * 推送报告返回结果信息
     */
    @TableField("response_msg")
    private String responseMsg;

    /**
     * 最后一次推送时间
     */
    @TableField("last_push_time")
    private LocalDateTime lastPushTime;

    /**
     * 推送报告次数
     */
    @TableField("response_count")
    private Integer responseCount;

    /**
     * 推送结果（1 - 未推送  -> 0 - 已入队 待处理；2 - 已推送 - > 80 - 已送达；99 - 递送结果报告失败，超过最大推送次数；）
     */
    @TableField("response_flag")
    private Integer responseFlag;

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
