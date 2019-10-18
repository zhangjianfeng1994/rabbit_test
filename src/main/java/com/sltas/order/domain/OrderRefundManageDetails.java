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
 * 退款管理详情表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderRefundManageDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 退费订单号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 受理人id
     */
    @TableField("accept_reuser_id")
    private Integer acceptReuserId;

    /**
     * 受理人名称
     */
    @TableField("accept_reuser_name")
    private String acceptReuserName;

    /**
     * 受理人机构id
     */
    @TableField("accept_revieworg_id")
    private Integer acceptRevieworgId;

    /**
     * 受理人审核时间
     */
    @TableField("accept_review_time")
    private LocalDateTime acceptReviewTime;

    /**
     * 受理人意见
     */
    @TableField("accept_review_opinion")
    private String acceptReviewOpinion;

    /**
     * 退单业务审核人
     */
    @TableField("business_reuser_id")
    private Integer businessReuserId;

    /**
     * 退单业务审核人名称
     */
    @TableField("business_reuser_name")
    private String businessReuserName;

    /**
     * 退单业务审核人机构
     */
    @TableField("business_revieworg_id")
    private Integer businessRevieworgId;

    /**
     * 退单业务审核时间
     */
    @TableField("business_review_time")
    private LocalDateTime businessReviewTime;

    /**
     * 退单业务审核意见
     */
    @TableField("business_review_opinion")
    private String businessReviewOpinion;

    /**
     * 退单财务经办人
     */
    @TableField("agent_reuser_id")
    private Integer agentReuserId;

    /**
     * 退单财务经办人名称
     */
    @TableField("agent_reuser_name")
    private String agentReuserName;

    /**
     * 退单财务经办人机构
     */
    @TableField("agent_revieworg_id")
    private Integer agentRevieworgId;

    /**
     * 退单财务经办时间
     */
    @TableField("agent_review_time")
    private LocalDateTime agentReviewTime;

    /**
     * 退单财务经办意见
     */
    @TableField("agent_review_opinion")
    private String agentReviewOpinion;

    /**
     * 退单财务审核人
     */
    @TableField("finance_reuser_id")
    private Integer financeReuserId;

    /**
     * 退单财务审核人名称
     */
    @TableField("finance_reuser_name")
    private String financeReuserName;

    /**
     * 退单财务审核人机构
     */
    @TableField("finance_revieworg_id")
    private Integer financeRevieworgId;

    /**
     * 退单财务审核时间
     */
    @TableField("finance_review_time")
    private LocalDateTime financeReviewTime;

    /**
     * 退单财务审核意见
     */
    @TableField("finance_review_opinion")
    private String financeReviewOpinion;

    /**
     * 退单有权审批人
     */
    @TableField("rights_reuser_id")
    private Integer rightsReuserId;

    /**
     * 退单有权审批人名称
     */
    @TableField("rights_reuser_name")
    private String rightsReuserName;

    /**
     * 退单有权审批人机构
     */
    @TableField("rights_revieworg_id")
    private Integer rightsRevieworgId;

    /**
     * 退单有权审批时间
     */
    @TableField("rights_review_time")
    private LocalDateTime rightsReviewTime;

    /**
     * 退单有权审批意见
     */
    @TableField("rights_review_opinion")
    private String rightsReviewOpinion;

    /**
     * 退单出纳人
     */
    @TableField("cashier_reuser_id")
    private Integer cashierReuserId;

    /**
     * 退单出纳人名称
     */
    @TableField("cashier_reuser_name")
    private String cashierReuserName;

    /**
     * 退单出纳人机构
     */
    @TableField("cashier_revieworg_id")
    private Integer cashierRevieworgId;

    /**
     * 退单出纳时间
     */
    @TableField("cashier_review_time")
    private LocalDateTime cashierReviewTime;

    /**
     * 退单出纳意见
     */
    @TableField("cashier_review_opinion")
    private String cashierReviewOpinion;

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
