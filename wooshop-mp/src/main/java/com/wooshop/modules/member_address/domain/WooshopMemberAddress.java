

package com.wooshop.modules.member_address.domain;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;




/**
* @author woo
* @date 2021-12-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_member_address")
public class WooshopMemberAddress extends BaseDomain {
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "会员id")
//    @NotNull
    private Long uid;

    @ApiModelProperty(value = "姓名")
//    @NotBlank
    private String memberName;

    @ApiModelProperty(value = "电话")
//    @NotBlank
    private String memberMobile;

    @ApiModelProperty(value = "所在省")
//    @NotBlank
    private String provinceName;

    @ApiModelProperty(value = "所在市")
//    @NotBlank
    private String cityName;

    @ApiModelProperty(value = "城市id")
//    @NotNull
    private Integer cityId;

    @ApiModelProperty(value = "所在区")
//    @NotBlank
    private String district;

    @ApiModelProperty(value = "详细地址")
//    @NotBlank
    private String detailedAddress;

    @ApiModelProperty(value = "邮编")
//    @NotNull
    private Integer postCode;

    @ApiModelProperty(value = "经度")
//    @NotBlank
    private String longitude;

    @ApiModelProperty(value = "纬度")
//    @NotBlank
    private String latitude;

    @ApiModelProperty(value = "是否为默认收货地址")
//    @NotNull
    private Integer isDefault;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "地址标签:0家、1公司、2学校")
    private Integer tagType;



    public void copy(WooshopMemberAddress source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
