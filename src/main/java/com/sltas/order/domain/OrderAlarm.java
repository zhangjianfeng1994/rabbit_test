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
 * 系统告警表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderAlarm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 业务类型
     */
    @TableField("service_type")
    private String serviceType;

    /**
     * 业务子类型
     */
    @TableField("service_subtype")
    private String serviceSubtype;

    /**
     * 业务子类型名称
     */
    @TableField("service_subtype_name")
    private String serviceSubtypeName;

    /**
     * 告警优先级 （100 - 非紧急异常,可事后进行处理；101 - 程序重大异常，需紧急处理；102 - xxxxx 后续补全；）
     */
    @TableField("level_code")
    private Integer levelCode;

    /**
     * 是否邮件告警 （0 未告警 ；1 告知；）
     */
    @TableField("is_email")
    private Integer isEmail;

    /**
     * 是否短信告警（0 未告警 ；1 告知；）
     */
    @TableField("is_phone")
    private Integer isPhone;

    /**
     * 是否处理完毕（0 未处理；1 已处理；）
     */
    @TableField("is_close")
    private Integer isClose;

    /**
     * 告警信息明细
     */
    @TableField("notice_info")
    private String noticeInfo;

    /**
     * 程序错误信息
     */
    @TableField("error_info")
    private String errorInfo;

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
