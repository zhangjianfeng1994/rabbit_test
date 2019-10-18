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
 * 发票子项关系表
 * </p>
 *
 * @author zsy
 * @since 2019-03-04
 */
@Data
@Accessors(chain = true)
public class OrderItemInvoiceRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 支付订单子项号 (订单子项主键)
     */
    @TableField("item_no")
    private String itemNo;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 开票表id
     */
    @TableField("order_invoice_id")
    private Long orderInvoiceId;

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
