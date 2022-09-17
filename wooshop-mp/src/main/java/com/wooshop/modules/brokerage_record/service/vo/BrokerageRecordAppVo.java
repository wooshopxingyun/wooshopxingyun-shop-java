package com.wooshop.modules.brokerage_record.service.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 分销佣金统计响应对象
 */
@Data
public class BrokerageRecordAppVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前累计佣金金额")
    private BigDecimal nowBrokeragePrice;

    @ApiModelProperty(value = "累计获得佣金")
    private BigDecimal totalBrokeragePrice;

    @ApiModelProperty(value = "累计已经提现佣金")
    private BigDecimal totalWithdrawBrokeragePrice;

    @ApiModelProperty(value = "昨日获得佣金")
    private BigDecimal yesterdayTotalBrokeragePrice;
}
