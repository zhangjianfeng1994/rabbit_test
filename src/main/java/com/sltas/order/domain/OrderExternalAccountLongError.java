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
 * 补录外部订单长款对账错误信息记录表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderExternalAccountLongError implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 支付流水表id
     */
    @TableField("pay_id")
    private Long payId;
    
    /**
         * 订单流水号
    * pay_id 支付订单流水号 / 
    * pay_details_id 支付订单流水详情号
        * 均作为订单的支付流水号进行对账操作
    */
    @TableField("serial_number_no")
    private String serialNumberNo;

    /**
          *   订单号
     * trans_no 支付订单号 /
     * pay_details_id 支付订单流水详情号
          *   其中合并支付的流水头和流水信息均为支付订单流水详情号
     */
    @TableField("serial_header_no")
    private String serialHeaderNo;
    
    /**
     * 支付前类型（0 - 普通支付；1 - 合并支付；）
     */
    @TableField("before_pay_type")
    private Integer beforePayType;
    
    /**
     * 工具平台业务流水号（盒子发起退款因没有订单这面的请求订单及流水，只能通过支付平台流水号进行退费操作）
     */
    @TableField("sl_busi_id")
    private String slBusiId;

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
