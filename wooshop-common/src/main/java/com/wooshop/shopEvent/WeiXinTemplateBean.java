package com.wooshop.shopEvent;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * 自定义微信提现模板 bean
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeiXinTemplateBean {

    @ApiModelProperty(value = "微信模板类型")
    private Integer templateType;

    @ApiModelProperty(value = "是否拼团订单自动取消:1是")
    private Integer pinkType;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "时间")
    private String time;

    @ApiModelProperty(value = "金额")
    private String price;

    @ApiModelProperty(value = "交付名称")
    private String deliveryName;

    @ApiModelProperty(value = "交付id")
    private String deliveryId;

    @ApiModelProperty(value = "支付时间")
    private String payType;

    @ApiModelProperty(value = "用户id")
    private Long uid;

    @ApiModelProperty(value = "用户申请提现唯一id")
    private Long extractId;
}
