package com.wooshop.modules.goods.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2021-12-05
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopStoreGoodsAttrDetailsDto implements Serializable {

    @ApiModelProperty(value = "属性详情参数表")
    private Long id;

    @ApiModelProperty(value = "关联商品id")
    private Long goodsId;

    @ApiModelProperty(value = "属性参数 json")
    private String attrText;

    @ApiModelProperty(value = "活动类型 0普通商品 1秒杀 2砍价 3拼团")
    private Integer activityType;

    @ApiModelProperty(value = "逻辑删除 1删除")
    private Integer isDel;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "规格原始数据")
    private String specorig;

    @ApiModelProperty(value = "规格参数")
    private String sprcificationparlams;
}
