package com.wooshop.modules.brokerage_record.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="BrokerageOrderAppVo对象", description="推广订单信息")
public class BrokerageOrderAppVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "推广条数")
    private Integer count = 0;

    @ApiModelProperty(value = "推广年月")
    private String broTime;

    @ApiModelProperty(value = "推广订单信息")
    private List<UserBrokerageOrderAppVo> childUserBroOrder = new ArrayList<>();
}
