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
 * 订单物流信息表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderItemLogistics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 订单子项号
     */
    @TableField("item_no")
    private String itemNo;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 收货人姓名
     */
    @TableField("consignee_name")
    private String consigneeName;

    /**
     * 收货人联系方式
     */
    @TableField("consignee_phone")
    private String consigneePhone;

    /**
     * 收货地址
     */
    @TableField("consignee_address")
    private String consigneeAddress;

    /**
     * 物流名称
     */
    @TableField("logistics_name")
    private String logisticsName;

    /**
     * 物流单号
     */
    @TableField("logistics_number")
    private String logisticsNumber;

    /**
     * 发货时间
     */
    @TableField("gmt_shipments")
    private LocalDateTime gmtShipments;

    /**
     * 收货时间
     */
    @TableField("gmt_receiving")
    private LocalDateTime gmtReceiving;

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
