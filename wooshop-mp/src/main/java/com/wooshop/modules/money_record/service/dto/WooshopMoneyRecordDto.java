package com.wooshop.modules.money_record.service.dto;


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
public class WooshopMoneyRecordDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long uid;

    @ApiModelProperty(value = "充值订单id")
    private String orderId;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal money;

    @ApiModelProperty(value = "赠送金额")
    private BigDecimal giveMoney;

    @ApiModelProperty(value = "充值类型")
    private String moneyType;

    @ApiModelProperty(value = "是否充值金额")
    private Integer isPaid;

    @ApiModelProperty(value = "充值金额支付时间")
    private Date payTime;

    @ApiModelProperty(value = "充值金额时间")
    private Date createTime;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundMoney;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 1删除")
    private Integer isDel;
}
