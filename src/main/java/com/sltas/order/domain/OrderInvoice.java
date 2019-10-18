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
 * 发票表
 * </p>
 *
 * @author zsy
 * @since 2019-03-29
 */
@Data
@Accessors(chain = true)
public class OrderInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 开票头流水
     */
    @TableField("invoice_header_no")
    private String invoiceHeaderNo;

    /**
     * 开票时间
     */
    @TableField("invoice_time")
    private LocalDateTime invoiceTime;

    /**
     * 发票类型  ： 1电子发票   2纸质发票
     */
    @TableField("invoice_type")
    private Integer invoiceType;

    /**
     * 开票子项个数
     */
    @TableField("item_sum")
    private Integer itemSum;

    /**
     * 发票状态 （80 - 有效发票；99 - 冲红+作废，无效发票；）
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
