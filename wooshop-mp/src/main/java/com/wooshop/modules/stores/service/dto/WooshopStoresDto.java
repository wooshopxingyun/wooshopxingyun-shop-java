package com.wooshop.modules.stores.service.dto;


import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2021-12-21
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopStoresDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "店铺名称")
    private String storesName;

    @ApiModelProperty(value = "店铺信息")
    private String storesInfo;

    @ApiModelProperty(value = "店铺联系手机号码")
    private String telephone;

    @ApiModelProperty(value = "店铺联系称呼")
    private String contactName;

    @ApiModelProperty(value = "省市区")
    private String storesAddress;

    @ApiModelProperty(value = "省市区")
    private String storesAddressName;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "门店logo")
    private String storesLogo;

    @ApiModelProperty(value = "店铺地址纬度")
    private String latitude;

    @ApiModelProperty(value = "店铺地址经度")
    private String longitude;

    @ApiModelProperty(value = "允许核销有效日期")
    private String validTime;

    @ApiModelProperty(value = "营业时间")
    private String dayTime;

    @ApiModelProperty(value = "店铺状态:1开启 0隐藏")
    private Integer isStart;

    @ApiModelProperty(value = "是否删除")
    private Integer isDel;

    @ApiModelProperty(value = "是否允许自提:1允许 0不允许")
    private Integer isTake;

    @ApiModelProperty(value = "添加时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "管理用户id")
    private Long uid;

    @ApiModelProperty(value = "0 需要审核 并且待审核，1 不需要审核 2需要审核 且审核通过 3 需要审核 且审核未通过")
    private Integer isAuth;

    @ApiModelProperty(value = "审核信息")
    private String authMessage;

    @ApiModelProperty(value = "排序")
    private Integer sort;

//    @ApiModelProperty(value = "省市区")
//    @TableField(exist = false)
//    private String storesAddressName;

    @ApiModelProperty(value = "店铺距离: 移动端")
//    @TableField(exist = false)
    private String storesDistance;
}
