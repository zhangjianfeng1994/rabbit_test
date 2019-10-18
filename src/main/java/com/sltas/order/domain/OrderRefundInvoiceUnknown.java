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
 * 退票未知检索表
 * </p>
 *
 * @author zsy
 * @since 2019-03-04
 */
@Data
@Accessors(chain = true)
@Document(collection="order_refund_invoice_unknown")
public class OrderRefundInvoiceUnknown implements Serializable {

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
     * 退票表主键
     */
    @Field("order_refund_invoice_id")
    private Long orderRefundInvoiceId;
    

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
