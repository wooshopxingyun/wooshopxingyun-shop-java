package com.wooshop.modules.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户积分统计
 */
@Data
public class MyUserIntegralCountAppVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前积分")
    private Integer nowIntegral;

    @ApiModelProperty(value = "累计积分")
    private Integer totalIntegral;

    @ApiModelProperty(value = "累计消费积分")
    private Integer totalConsumeIntegral;

    @ApiModelProperty(value = "今日获得积分")
    private Integer getIntegralDay;

}
