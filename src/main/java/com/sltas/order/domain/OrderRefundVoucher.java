package com.sltas.order.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.gson.Gson;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 退凭证表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderRefundVoucher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 业务类型（1.退款业务；当前业务场景，仅有退款业务使用；）
     */
    @TableField("business_type")
    private Integer businessType;

    /**
     * 退凭证订单子项号
     */
    @TableField("item_no")
    private String itemNo;

    /**
     * 响应结果时间
     */
    @TableField("response_time")
    private LocalDateTime responseTime;

    /**
     * 处理结果类型（S-业务受理成功； F-业务受理失败（技术类校验失败））
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
     * 退凭证状态 （0   - 核准状态；70 - 调起凭证未知；80 - 退凭证成功；99 - 退凭证失败；特殊注意：当调用凭证结果异常时，，默认调用时是0 -核准状态，超时异常后 70 - 调起凭证未知,等待mongo job的轮询结果）
     */
    @TableField("status_id")
    private Integer statusId;

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
