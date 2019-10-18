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
 * 订单退款进度历史表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderRefundPlanLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 退款订单号
     */
    @TableField("trans_no")
    private String transNo;
    
    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 支付订单号
     */
    @TableField("old_item_no")
    private String oldItemNo;

    /**
     * 0-待入队时间
     */
    @TableField("add_queue_time")
    private LocalDateTime addQueueTime;
    
    /**
     * 0- 待入队 状态下入队次数
     */
    @TableField("add_queue_count")
    private Integer addQueueCount;

    /**
     * 1-退凭证待执行时间
     */
    @TableField("refund_voucher_exec_time")
    private LocalDateTime refundVoucherExecTime;

    /**
     * 2-退凭证中时间
     */
    @TableField("refund_voucher_ing_time")
    private LocalDateTime refundVoucherIngTime;

    /**
     * 3-退票待执时间
     */
    @TableField("refund_invoice_exec_time")
    private LocalDateTime refundInvoiceExecTime;

    /**
     * 4-退票中时间
     */
    @TableField("refund_invoice_ing_time")
    private LocalDateTime refundInvoiceIngTime;

    /**
     * 5-退款待执行时间
     */
    @TableField("refund_pay_exec_time")
    private LocalDateTime refundPayExecTime;

    /**
     * 6-退款中时间
     */
    @TableField("refund_pay_ing_time")
    private LocalDateTime refundPayIngTime;

    /**
     * 最后执行变更时间（已防止队列没有成功消费导致流程没有继续进行）
     */
    @TableField("last_operating_time")
    private LocalDateTime lastOperatingTime;
    
    /**
     * 受理退款失败描述（支付平台返回 or 平台原因） 
     */
    @TableField("result_message")
    private String resultMessage;

    /**
     * 退款流程进度状态：1.在首次发起退款中并已经成功入队，可直接变成 1 状态，让按照 数字顺序依次进行操作2.如果退款过程中有票据存在，则退票失败后，直接宣告退款失败。3.针退款失败 对于 channel_source 退款来源为第三方退款的，则订单直接进行 99 - ORDER_CANCELLED取消订单 (退款失败)，否则 标记为 受理中，继续等待下次管理人员出发 0 - ORDER_REFUNDACCEPTED退款受理中；新旧状态切换及定义：新增--> 0 待入队 (订单可能因某些情况未能成功在队列中消费，或者部分异常导致未能处理，需将此字段刷为0，经过后台job刷选后，重新放入队列，, 订单状态保持为 ITEM_REFUND 退款中)；-> 1 退凭证待执行(检查是否存在凭证信息redis中，如果存在则进行凭证申退)-> 2 退凭证中(从退凭证队列中获取将要进行申退的凭证信息，并发起凭证申退, PS重要：对于凭证进行操作记录的信息，根据当前业务雏形仅有退款会由我方发起申退请求，其他则不存在该情况，而且凭证必然绑定到 item_no 的上，所以交互直接是item_no的信息,而允许进行发起退凭证的操作，冲正时告知作用并不是发起，所以只存在退款情况，退款信息数量并不大，所以不考虑分表问题，而操作完的数据均会进入历史表)；05 -> 3 退票待执行 (获取到准备退款动作,并验证存在发票,将退票信息保存到退票队列中)；06 -> 4 退票中 (从退票队列中获取将要进行退票的数据,执行退票，等待响应, 订单状态保持为 ITEM_REFUND 退款中)；01 -> 5 退款待执行 (获取发票响应结果，如果成功退票，将进行后续退款动作存入 退款队列中，订单状态保持为 ITEM_REFUND 退款中)；02 -> 6 退款中 (从退款队列中获取将要进行退款的数据，执行退款，等待响应，订单状态保持为 ITEM_REFUND 退款中)；04 -> 80 退款成功 (通知调用者，退款成功)；03 -> 99 退款失败 (如果 channel_source 为第三方退款，则直接进行 99 - ORDER_CANCELLED取消订单 , 0 - ORDER_REFUNDACCEPTED退款受理中；"
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
