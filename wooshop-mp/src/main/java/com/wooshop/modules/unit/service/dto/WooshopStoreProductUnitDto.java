package com.wooshop.modules.unit.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2021-11-24
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooshopStoreProductUnitDto implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "单位名称")
    private String name;

    @ApiModelProperty(value = "状态：1开启、0关闭")
    private Integer isStart;

    @ApiModelProperty(value = "删除：1删除")
    private Integer isDel;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    private Long uid;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    private Long storeId;
}
