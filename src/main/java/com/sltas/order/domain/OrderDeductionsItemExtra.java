package com.sltas.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.gson.Gson;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 批扣订单子项附属表
 * </p>
 *
 * @author zsy
 * @since 2019-01-09
 */
@Data
@Accessors(chain = true)
public class OrderDeductionsItemExtra implements Serializable {

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
     * 银行账户ID
     */
    @TableField("bank_account_id")
    private Integer bankAccountId;

    /**
     * 业务模式(1 B2C，2 B2B)
     */
    @TableField("order_model")
    private Integer orderModel;

    /**
     * 产品制造方-发布商户id
     */
    @TableField("manu_merch_id")
    private Integer manuMerchId;

    /**
     * 产品制造方-发布机构id
     */
    @TableField("manu_organ_id")
    private Integer manuOrganId;

    /**
     * 产品制造方-联营商户id
     */
    @TableField("manu_assoc_id")
    private Integer manuAssocId;

    /**
     * 产品制造方-联营商户管理机构id
     */
    @TableField("manu_assorgan_id")
    private Integer manuAssorganId;

    /**
     * 产品制造方-联营协议id
     */
    @TableField("manu_assproto_id")
    private Integer manuAssprotoId;

    /**
     * 产品归属方-商户id
     */
    @TableField("owner_merch_id")
    private Integer ownerMerchId;

    /**
     * 产品归属方-组织机构id
     */
    @TableField("owner_organ_id")
    private Integer ownerOrganId;

    /**
     * 产品提供方-商户id
     */
    @TableField("provid_merch_id")
    private Integer providMerchId;

    /**
     * 产品提供方-商户名称
     */
    @TableField("provid_merch_name")
    private String providMerchName;

    /**
     * 产品提供方-机构id
     */
    @TableField("provid_organ_id")
    private Integer providOrganId;

    /**
     * 产品提供方-机构名称
     */
    @TableField("provid_organ_name")
    private String providOrganName;

    /**
     * 产品提供方-代销协议
     */
    @TableField("provid_proto_id")
    private Integer providProtoId;

    /**
     * 产品审核商户id
     */
    @TableField("oper_review_merchant")
    private Integer operReviewMerchant;

    /**
     * 产品审核商户名称
     */
    @TableField("oper_review_merchant_name")
    private String operReviewMerchantName;

    /**
     * 产品审核机构id
     */
    @TableField("oper_review_organ")
    private Integer operReviewOrgan;

    /**
     * 产品审核机构名称
     */
    @TableField("oper_review_organ_name")
    private String operReviewOrganName;

    /**
     * 销售方-商户id
     */
    @TableField("sale_merch_id")
    private Integer saleMerchId;

    /**
     * 销售方-组织机构id
     */
    @TableField("sale_organ_id")
    private Integer saleOrganId;

    /**
     * 销售方-销售人员
     */
    @TableField("sale_by_user")
    private Integer saleByUser;

    /**
     * 销售方-代销折扣
     */
    @TableField("sale_discount")
    private BigDecimal saleDiscount;

    /**
     * 产品资金结算方-商户id
     */
    @TableField("settle_merch_id")
    private Integer settleMerchId;

    /**
     * 产品资金结算方-管理机构id
     */
    @TableField("settle_organ_id")
    private Integer settleOrganId;

    /**
     * 产品业务受理方-商户id
     */
    @TableField("accept_merch_id")
    private Integer acceptMerchId;

    /**
     * 产品业务受理方-组织机构id
     */
    @TableField("accept_organ_id")
    private Integer acceptOrganId;

    /**
     * 当前管理商户id
     */
    @TableField("manage_merch_id")
    private Integer manageMerchId;

    /**
     * 当前管理机构id
     */
    @TableField("manage_organ_id")
    private Integer manageOrganId;

    /**
     * 产品信息-组合产品id
     */
    @TableField("prod_ckd_id")
    private Integer prodCkdId;

    /**
     * 产品信息-SKU识别码
     */
    @TableField("prod_sku_id")
    private Integer prodSkuId;

    /**
     * 产品信息-发布类型 （1自有产品   2联营产品）
     */
    @TableField("prod_issue_type")
    private Integer prodIssueType;

    /**
     * 周期编码
     */
    @TableField("period_code")
    private String periodCode;

    /**
     * 产品退费间隔时间
     */
    @TableField("prod_refund_time_interval")
    private Integer prodRefundTimeInterval;

    /**
     * 人员编号
     */
    @TableField("cust_ident_code")
    private String custIdentCode;

    /**
     * 客户业务主体-商户id
     */
    @TableField("cust_merch_id")
    private Integer custMerchId;

    /**
     * 客户业务主体-人员机构id
     */
    @TableField("cust_organ_id")
    private Integer custOrganId;

    /**
     * 交易人-用户id
     */
    @TableField("tran_user_id")
    private Integer tranUserId;

    /**
     * 交易人-用户名称
     */
    @TableField("tran_user_name")
    private String tranUserName;

    /**
     * 交易人-组织机构id
     */
    @TableField("tran_organ_id")
    private Integer tranOrganId;

    /**
     * 交易人-组织机构名称
     */
    @TableField("tran_organ_name")
    private String tranOrganName;

    /**
     * 交易人-联系电话
     */
    @TableField("tran_user_mobile")
    private String tranUserMobile;

    /**
     * 操作人-用户id
     */
    @TableField("oper_user_id")
    private Integer operUserId;

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
