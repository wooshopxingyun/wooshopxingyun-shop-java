package com.wooshop.modules.experience_record.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2022-03-02
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopUserExperienceRecordDto implements Serializable {

    @ApiModelProperty(value = "经验记录表主键id")
    private Long id;

    @ApiModelProperty(value = "用户uid")
    private Long uid;

    @ApiModelProperty(value = "关联id-orderNo,(sign,system默认为0）")
    private String linkId;

    @ApiModelProperty(value = "关联类型（order,sign,system）")
    private String linkType;

    @ApiModelProperty(value = "类型：1-增加，2-扣减")
    private Integer recordType;

    @ApiModelProperty(value = "标题")
    private String recordTitle;

    @ApiModelProperty(value = "经验")
    private Integer experience;

    @ApiModelProperty(value = "剩余")
    private Integer balance;

    @ApiModelProperty(value = "备注")
    private String mark;

    @ApiModelProperty(value = "状态：1-成功（保留字段）0-失败")
    private Integer isStatus;

    @ApiModelProperty(value = "添加时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除: 1-删除")
    private Integer isDel;
}
