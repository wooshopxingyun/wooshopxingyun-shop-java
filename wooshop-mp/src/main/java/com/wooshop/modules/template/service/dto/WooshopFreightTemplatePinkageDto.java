package com.wooshop.modules.template.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;


/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooshopFreightTemplatePinkageDto implements Serializable {

    @ApiModelProperty(value = "免邮费编号")
    private Long id;

    @ApiModelProperty(value = "模板ID")
    private Long templateId;

    @ApiModelProperty(value = "城市ID")
    private Long cityId;

    @ApiModelProperty(value = "城市id和城市表父类id")
    private String area;

    @ApiModelProperty(value = "包邮件数")
    private BigDecimal number;

    @ApiModelProperty(value = "包邮金额")
    private BigDecimal price;

    @ApiModelProperty(value = "运费计费类型：1按体积计费、2按重量计费、3按件数计费")
    private Integer type;

    @ApiModelProperty(value = "免运费分组唯一值")
    private String pinkageUuid;

    @ApiModelProperty(value = "是否开启")
    private Integer isStart;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "物理删除 1表示删除")
    private Integer isDel;
}
