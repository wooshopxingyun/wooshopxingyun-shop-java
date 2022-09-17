package com.wooshop.modules.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 我的账号统计
 */
@Data
public class MyAccountCountVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户总余额")
    private BigDecimal nowTotalMoney;

    @ApiModelProperty(value = "累计充值金额")
    private BigDecimal totalPayMoney;

    @ApiModelProperty(value = "累计消费金额")
    private BigDecimal totalConsumeMoney;

    @ApiModelProperty(value = "充值开关")
    private Integer rechargeOff;

}
