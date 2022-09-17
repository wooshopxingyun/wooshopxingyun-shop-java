package com.wooshop.modules.home_total;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="HomeRateVo对象", description="主页统计数据对象")
public class HomeRateVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "今日销售额")
    private BigDecimal sales;

    @ApiModelProperty(value = "总销售额")
    private BigDecimal totalSales;

    @ApiModelProperty(value = "今日访问量")
    private Integer pageviews;

    @ApiModelProperty(value = "今日订单量")
    private Integer orderNum;

    @ApiModelProperty(value = "本月订单量")
    private Integer monthOrderNum;

    @ApiModelProperty(value = "总订单量")
    private Integer totalOrderNum;

    @ApiModelProperty(value = "今日新增用户")
    private Integer newUserNum;

    @ApiModelProperty(value = "总用户")
    private Integer totalUserNum;

}
