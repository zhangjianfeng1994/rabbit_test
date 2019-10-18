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
 * 订单批量补录详情表
 * </p>
 *
 * @author zsy
 * @since 2019-04-08
 */
@Data
@Accessors(chain = true)
public class OrderBackBatchDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 前台流水号（单笔为单笔前台流水，批量为批量请求的前台流水号）
     */
    @TableField("third_party_no")
    private String thirdPartyNo;

    /**
     * 批量补录主键
     */
    @TableField("order_back_batch_id")
    private Long orderBackBatchId;

    /**
     * 订单号（支付+退款）
     */
    @TableField("trans_no")
    private String transNo;

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
