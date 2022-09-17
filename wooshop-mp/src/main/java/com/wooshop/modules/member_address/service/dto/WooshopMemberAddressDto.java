package com.wooshop.modules.member_address.service.dto;


import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2021-12-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopMemberAddressDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "会员id")
    private Long uid;

    @ApiModelProperty(value = "姓名")
    private String memberName;

    @ApiModelProperty(value = "电话")
    private String memberMobile;

    @ApiModelProperty(value = "所在省")
    private String provinceName;

    @ApiModelProperty(value = "所在市")
    private String cityName;

    @ApiModelProperty(value = "城市id")
    private Integer cityId;

    @ApiModelProperty(value = "所在区")
    private String district;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "邮编")
    private Integer postCode;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "是否为默认收货地址")
    private Boolean isDefault;

    @ApiModelProperty(value = "是否删除")
    private Integer isDel;
//
//    @ApiModelProperty(value = "创建时间")
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
//    private Date createTime;
//
//    @ApiModelProperty(value = "更新时间")
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
//    private Date updateTime;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "地址标签:0家、1公司、2学校")
    private Integer tagType;
}
