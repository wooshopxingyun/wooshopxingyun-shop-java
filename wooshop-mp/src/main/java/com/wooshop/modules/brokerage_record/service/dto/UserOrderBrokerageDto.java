package com.wooshop.modules.brokerage_record.service.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserOrderBrokerageDto {

    @ApiModelProperty(value = "订单数量")
    private Integer orderNum=0;

    @ApiModelProperty(value = "订单合计金额")
    private BigDecimal orderPrice=new BigDecimal(0.00);
}
