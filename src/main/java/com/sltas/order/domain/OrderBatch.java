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
 * 订单批扣批次表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 业务类型（1 - BILLS_ORDER账单订单；2 - SALES_ORDER销售订单；3 - RECHARGE_ORDER充值订单；4 - TRANSFER_ORDER转账订单；）
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 商量内部系统批次请求流水
     */
    @TableField("third_party_batch_no")
    private String thirdPartyBatchNo;

    /**
     * 商户编号(产品系统获取  -> 上送支付平台 请求头中的 merchantid 商户号)
     */
    @TableField("merchant_code")
    private String merchantCode;

    /**
     * 交易类型（1银行卡标准代扣；2签约银行卡协议号代扣；）
     */
    @TableField("tx_type")
    private Integer txType;

    /**
     * 总金额 单位： 元
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 总笔数
     */
    @TableField("total_count")
    private Integer totalCount;

    /**
     * 后台回调URL
     */
    @TableField("receive_url")
    private String receiveUrl;

    /**
     * 商户自定义附言
     */
    @TableField("post_script")
    private String postScript;

    /**
     * 程序处理时长 单位： 秒
     */
    @TableField("handling_time")
    private Integer handlingTime;

    /**
     * 逻辑检索时间（同步批扣状态，首次批扣完成预计算下次检索时间,首次检索可能与轮询赋值不同）
     */
    @TableField("select_time")
    private LocalDateTime selectTime;

    /**
     * 请求次数（批量订单支付未知状态下 BATCH_APPROVED 批量订单核准订单（未知）有job进行条件匹配检索 Def 0 每次+）
     */
    @TableField("request_count")
    private Integer requestCount;

    /**
     * 工具平台业务流水号（盒子发起退款因没有订单这面的请求订单及流水，只能通过支付平台流水号进行退费操作）
     */
    @TableField("sl_busi_id")
    private String slBusiId;

    /**
     * 是否计费（0-代表不计费；1-代表计费；）
     */
    @TableField("paid")
    private String paid;

    /**
     * 商联响应时间
     */
    @TableField("response_time")
    private String responseTime;

    /**
     * 处理结果类型（S-业务受理成功 ；F-业务受理失败（技术类校验失败）； R-业务受理异常结果未知）
     */
    @TableField("result_type")
    private String resultType;

    /**
     * 订单支付状态
     */
    @TableField("body_status")
    private String bodyStatus;

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
     * 成功总金额，单位：分
     */
    @TableField("success_total_amount")
    private Long successTotalAmount;

    /**
     * 成功总笔数
     */
    @TableField("success_total_count")
    private Integer successTotalCount;

    /**
     * 手续费合计,实时计算手续费，并且支付成功时根据配置可返回
     */
    @TableField("counter_fee")
    private Integer counterFee;

    /**
     * 批次处理结算时间
     */
    @TableField("batch_end_time")
    private Integer batchEndTime;

    /**
     * 批扣状态（ 0 - BATCH_APPROVED 批量订单核准订单80 - BATCH_COMPLETED 批量订单完成订单 99 - BATCH_CANCELLED 批量订单取消订单 ；）
     */
    @TableField("status_id")
    private Integer statusId;

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
