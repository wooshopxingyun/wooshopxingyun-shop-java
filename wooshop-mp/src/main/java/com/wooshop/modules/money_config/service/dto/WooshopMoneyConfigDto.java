package com.wooshop.modules.money_config.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;


/**
* @author woo
* @date 2021-12-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopMoneyConfigDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal money;

    @ApiModelProperty(value = "赠送金额")
    private BigDecimal giveMoney;

    @ApiModelProperty(value = "排序 数字越小越靠前")
    private Integer sort;

    @ApiModelProperty(value = "逻辑删除 1删除")
    private Integer isDel;

    @ApiModelProperty(value = "状态:1显示 0不显示")
    private Integer isStart;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
