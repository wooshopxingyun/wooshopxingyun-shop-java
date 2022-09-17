package com.wooshop.modules.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@ApiModel(value="UserSpreadPeopleItemAppvo对象", description="推广人信息")
public class UserSpreadPeopleItemAppvo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户编号")
    private Long uid;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户头像")
    private String avatarPath;

    @ApiModelProperty(value = "添加时间")
    private String addTime;

    @ApiModelProperty(value = "推广人数")
    private Integer childCount;

    @ApiModelProperty(value = "推广订单数量")
    private Integer orderCount;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal numberCount;
}
