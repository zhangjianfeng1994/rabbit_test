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
 * 退票表
 * </p>
 *
 * @author zsy
 * @since 2019-03-06
 */
@Data
@Accessors(chain = true)
public class OrderRefundInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 业务类型（1.冲正业务；   2.退款业务 ；  3.发票业务；）
     */
    @TableField("business_type")
    private Integer businessType;

    /**
     * 订单号（冲正业务 填写支付订单号；退款业务 填写退款订单号；发票业务 此值不做填写；）
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 订单发票表主键
     */
    @TableField("order_invoice_id")
    private Long orderInvoiceId;

    /**
     * 开票头流水 (订单属于的开票头流水)
     */
    @TableField("invoice_header_no")
    private String invoiceHeaderNo;

    /**
     * 作废+冲红（涉及到的invoice_header_no 的order_item个数）
     */
    @TableField("item_sum")
    private Integer itemSum;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 冲红+作废 操作人id
     */
    @TableField("operator_user_id")
    private Integer operatorUserId;

    /**
     * 冲红+作废 操作人名称
     */
    @TableField("operator_user_name")
    private String operatorUserName;

    /**
     * 冲红+作废 操作人机构id
     */
    @TableField("operator_user_institution_id")
    private Integer operatorUserInstitutionId;

    /**
     * 冲红+作废 操作人机构名称
     */
    @TableField("operator_user_institution_name")
    private String operatorUserInstitutionName;

    /**
     * 冲红+作废 操作人意见
     */
    @TableField("operator_user_opinion")
    private String operatorUserOpinion;

    /**
     * 响应结果时间
     */
    @TableField("response_time")
    private LocalDateTime responseTime;

    /**
     * 处理结果类型（S-业务受理成功； F-业务受理失败（技术类校验失败）；）
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
     * 发票状态 （ 0 - 核准状态；80 - 退票成功； 99 - 退票失败；）
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
