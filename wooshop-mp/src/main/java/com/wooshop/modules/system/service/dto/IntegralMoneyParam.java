package com.wooshop.modules.system.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "修改积分/余额 请求参数对象")
@Data
public class IntegralMoneyParam implements Serializable {


    @ApiModelProperty(value = "用户id")
    @NotNull(message = "用户编码不能为空")
    private Long uid;

    @ApiModelProperty(value = "编辑类型:0-积分、1-余额")
    @NotNull(message = "编辑类型不能为空")
    @Size(max = 1,min = 0)
    private Integer genreType;

    @ApiModelProperty(value = "修改类型0-减少、1-增加")
    @NotNull(message = "修改类型不能为空")
    @Size(max = 1,min = 0)
    private Integer editType;

    @ApiModelProperty(value = "数量")
    @NotNull(message = "数量不能为空")
    private BigDecimal number;
}
