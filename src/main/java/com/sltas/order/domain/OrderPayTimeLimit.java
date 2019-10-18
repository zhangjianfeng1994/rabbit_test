package com.sltas.order.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.google.gson.Gson;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单支付时限表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
@Document(collection="order_pay_time_limit")
public class OrderPayTimeLimit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@Id
    private Long id;

    /**
     * 节点ID
     */
	@Field("node_id")
    private String nodeId;

    /**
     * 节点时限
     */
	@Field("node_time_out")
    private LocalDateTime nodeTimeOut;

    /**
     * 订单支付超时时间
     */
	@Field("pay_time_out")
    private LocalDateTime payTimeOut;

    /**
     * 启动时间
     */
	@Field("start_time")
    private LocalDateTime startTime;

    /**
     * 启动次数
     */
	@Field("start_count")
    private Integer startCount;

    /**
     * 支付订单号
     */
	@Field("trans_no")
    private String transNo;

    /**
     * 交易人-用户id
     */
	@Field("tran_user_id")
    private Integer tranUserId;

    /**
     * 创建时间
     */
	@Field("gmt_create")
    private LocalDateTime gmtCreate;

    @Override
	public String toString() {
		return new Gson().toJson(this);
	}	
}
