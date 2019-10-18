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
 * 订单冲正历史表 
 * </p>
 *
 * @author zsy
 * @since 2019-03-04
 */
@Data
@Accessors(chain = true)
public class OrderReversalLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 冲正订单交易流水号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 冲正前台流水号
     */
    @TableField("third_party_no")
    private String thirdPartyNo;

    /**
     * 业务类型（1 - BILLS_ORDER账单订单；2 - SALES_ORDER销售订单；3 - RECHARGE_ORDER充值订单；4 - TRANSFER_ORDER转账订单；）
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 冲正人识别码
     */
    @TableField("reversal_user_id")
    private Integer reversalUserId;

    /**
     * 冲正人名称
     */
    @TableField("reversal_user_name")
    private String reversalUserName;

    /**
     * 冲正人机构id
     */
    @TableField("reversal_party_id")
    private Integer reversalPartyId;

    /**
     * 冲正人机构名
     */
    @TableField("reversal_party_name")
    private String reversalPartyName;

    /**
     * 冲正描述
     */
    @TableField("reversal_describe")
    private String reversalDescribe;

    /**
     * 冲正后台结果
     */
    @TableField("reversal_result")
    private String reversalResult;

    /**
     * 冲正完成时间
     */
    @TableField("reversal_time")
    private LocalDateTime reversalTime;

    /**
     * 冲正状态（1 - REVERSAL_ING   冲正中； 80 - REVERSAL_COMPLETED   冲正完成  ； 99 - REVERSAL_CANCELLED    冲正失败）
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
