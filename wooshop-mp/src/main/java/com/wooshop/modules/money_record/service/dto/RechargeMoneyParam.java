package com.wooshop.modules.money_record.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RechargeMoneyParam implements Serializable {
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "充值金额")
    @DecimalMin(value = "0")
    private BigDecimal money;

    @NotBlank(message = "选择支付类型")
    @ApiModelProperty(value = "支付类型")
    private String payType;

    @ApiModelProperty(value = "设备类型")
    private String clientType;
}
