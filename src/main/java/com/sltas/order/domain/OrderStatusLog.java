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
 * 订单状态流水表    
 * </p>
 *
 * @author zsy
 * @since 2019-03-04
 */
@Data
@Accessors(chain = true)
public class OrderStatusLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 订单号 （支付+退款）
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 前台流水号
     */
    @TableField("third_party_no")
    private String thirdPartyNo;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

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
     * 订单状态标识
     */
    @TableField("status_id")
    private Integer statusId;

    /**
     * 订单状态描述
     */
    @TableField("status_describe")
    private String statusDescribe;

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

    @Override
   	public String toString() {
   		return new Gson().toJson(this);
   	}
}
