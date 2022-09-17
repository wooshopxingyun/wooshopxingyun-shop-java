package com.wooshop.modules.user_level.service.dto;


import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2022-04-16
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopUserLevelDto implements Serializable {

    private Long id;

    @ApiModelProperty(value = "用户uid")
    private Long uid;

    @ApiModelProperty(value = "等级vip")
    private Integer levelId;

    @ApiModelProperty(value = "会员等级")
    private Integer grade;

    @ApiModelProperty(value = "0:禁止,1:正常")
    private Integer isStart;

    @ApiModelProperty(value = "备注")
    private String mark;

    @ApiModelProperty(value = "是否已通知")
    private Integer remind;

    @ApiModelProperty(value = "是否删除,0=未删除,1=删除")
    private Integer isDel;

    @ApiModelProperty(value = "享受折扣")
    private Integer discount;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @ApiModelProperty(value = "过期时间")
    private Timestamp expiredTime;
}
