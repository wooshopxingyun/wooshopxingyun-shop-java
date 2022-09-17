package com.wooshop.modules.brokerage_record.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserBrokerageOrderAppVo对象", description="推广订单信息子集")
public class UserBrokerageOrderAppVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "返佣时间")
    private Date broTime;

    @ApiModelProperty(value = "返佣金额")
    private BigDecimal broNumber;

    @ApiModelProperty(value = "用户头像")
    private String avatarPath;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "订单显示类型")
    private String orderType;
}
