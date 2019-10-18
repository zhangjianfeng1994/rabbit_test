package com.sltas.order.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 开票批次表
 * </p>
 *
 * @author zsy
 * @since 2019-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderInvoiceBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 业务类型 （1.退票续开  ；2.支付完成开票  -> 0 - 订单系统主动开票；3.发票平台申请  -> 1 - 发票平台申请开票；）
     */
    @TableField("business_type")
    private Integer businessType;

    /**
     * 批次子项个数
     */
    @TableField("batch_count")
    private Integer batchCount;

    /**
     * 商联内部系统调用流水号（此值在business_type =0 - 订单系统主动开票 不存在值，因是系统内部调用非第三方）
     */
    @TableField("third_party_no")
    private String thirdPartyNo;

    /**
     * 逻辑检索时间（同步批扣状态，首次批扣完成预计算下次检索时间,首次检索可能与轮询赋值不同）
     */
    @TableField("select_time")
    private LocalDateTime selectTime;

    /**
     * 请求查询接口次数
     */
    @TableField("request_count")
    private Integer requestCount;

    /**
     * 请求耗时  (请求接口时间 S秒)
     */
    @TableField("request_time")
    private Integer requestTime;

    /**
     * 处理结果类型（S-业务受理成功 ；F-业务受理失败（技术类校验失败）；此值在  1 - 发票平台申请开票 不存在该组值，因是由发票平台起调）
     */
    @TableField("result_type")
    private String resultType;

    /**
     * 处理结果编码（此值在  1 - 发票平台申请开票 不存在该组值，因是由发票平台起调）
     */
    @TableField("result_code")
    private String resultCode;

    /**
     * 处理结果（此值在  1 - 发票平台申请开票 不存在该组值，因是由发票平台起调）
     */
    @TableField("result_msg")
    private String resultMsg;

    /**
     * 开票批次状态（0 - 请求异常待核准；   80 - 请求受理成功；99 - 请求受理失败；）
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
     * 结束时间
     */
    @TableField("gmt_end")
    private LocalDateTime gmtEnd;

    /**
     * 最后修改时间
     */
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;


}
