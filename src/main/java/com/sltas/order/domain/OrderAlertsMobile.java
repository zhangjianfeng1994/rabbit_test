package com.sltas.order.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.gson.Gson;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单报警手机号表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderAlertsMobile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 所属人
     */
    @TableField("person_name")
    private String personName;

    /**
     * 手机号
     */
    @TableField("mobile_number")
    private String mobileNumber;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 数据是否有效（80 - 有效；99 - 失效；）
     */
    @TableField("status_id")
    private Integer statusId;

    @Override
	public String toString() {
		return new Gson().toJson(this);
	}	
}
