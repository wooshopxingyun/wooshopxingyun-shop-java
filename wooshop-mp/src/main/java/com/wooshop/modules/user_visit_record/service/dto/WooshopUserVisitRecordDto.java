package com.wooshop.modules.user_visit_record.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2022-03-24
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopUserVisitRecordDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "创建时期")
    private Date createTime;

    @ApiModelProperty(value = "用户uid")
    private Long uid;

    @ApiModelProperty(value = "访问类型:1-首页，2-详情页，3-营销活动详情页，4-个人中心，5-购物车、")
    private Integer visitType;

    @ApiModelProperty(value = "访问ip地址")
    private String createIp;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "设备类型")
    private String clienttype;

    @ApiModelProperty(value = "0")
    private Integer isDel;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "商品类型:0非商品、1普通商品、2砍价商品、3团购商品、4秒杀商品、5积分商品")
    private Integer goodsType;
}
