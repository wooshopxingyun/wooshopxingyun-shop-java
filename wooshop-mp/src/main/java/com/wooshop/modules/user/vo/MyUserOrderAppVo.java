package com.wooshop.modules.user.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户信息
 */
@Data
public class MyUserOrderAppVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "待付款")
    private Integer waitOrder;

    @ApiModelProperty(value = "待发货")
    private Integer waitDeliverOrder;

    @ApiModelProperty(value = "待收货")
    private Integer alreadyOrder;

    @ApiModelProperty(value = "已收货 待评价")
    private Integer alreadyOrderEvaluate;

    @ApiModelProperty(value = "已成功")
    private Integer succeedOrderEvaluate;

    @ApiModelProperty(value = "退款中 退款申请")
    private Integer afterSale;

    @ApiModelProperty(value = "总订单数量")
    private Integer sumOrderNum;

    @ApiModelProperty(value = "总消费金额")
    private BigDecimal sumConsumePrice;

    /**
     * 售后统计
     */
    @ApiModelProperty(value = "全部退款订单数量")
    private Integer allAfterSale;

    @ApiModelProperty(value = "申请中 售后订单数量")
    private Integer ingAfterSale;

    @ApiModelProperty(value = "待退货 售后订单数量")
    private Integer stayAfterSale;

    @ApiModelProperty(value = "待退款 售后订单数量")
    private Integer refundAfterSale;

    @ApiModelProperty(value = "已退款 售后订单数量")
    private Integer succeedAfterSale;


}
