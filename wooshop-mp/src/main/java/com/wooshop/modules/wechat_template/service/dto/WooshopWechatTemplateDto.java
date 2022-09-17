package com.wooshop.modules.wechat_template.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2022-02-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopWechatTemplateDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "模板编号")
    private String tempCode;

    @ApiModelProperty(value = "模板名称")
    private String tempName;

    @ApiModelProperty(value = "回复内容")
    private String tempContent;

    @ApiModelProperty(value = "模板ID")
    private String tempId;

    @ApiModelProperty(value = "添加时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "状态:0不启用/1启用")
    private Integer isStatus;

    private Integer isDel;

    @ApiModelProperty(value = "类型：temp:模板消息 sub:订阅消息")
    private String tempType;
}
