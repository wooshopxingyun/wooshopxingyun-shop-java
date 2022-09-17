package com.wooshop.modules.brokerage_record.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;


/**
* @author woo
* @date 2022-04-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopUserBrokerageRecordDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "用户uid (推广人id)")
    private Long uid;

    @ApiModelProperty(value = "关联id（orderNo,提现id）")
    private String linkId;

    @ApiModelProperty(value = "关联类型（order,extract，yue）")
    private String linkType;

    @ApiModelProperty(value = "类型：1-增加，2-扣减（提现）")
    private Integer broType;

    @ApiModelProperty(value = "标题")
    private String broTitle;

    @ApiModelProperty(value = "金额")
    private BigDecimal broPrice;

    @ApiModelProperty(value = "剩余")
    private BigDecimal broBalance;

    @ApiModelProperty(value = "备注")
    private String broMark;

    @ApiModelProperty(value = "状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款），5-提现申请")
    private Integer isStart;

    @ApiModelProperty(value = "冻结期时间（天）")
    private Integer frozenTime;

    @ApiModelProperty(value = "解冻时间")
    private Long thawTime;

    @ApiModelProperty(value = "添加时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "分销等级")
    private Integer brokerageLevel;

    @ApiModelProperty(value = "逻辑删除:1表示删除、0默认")
    private Integer isDel;
}
