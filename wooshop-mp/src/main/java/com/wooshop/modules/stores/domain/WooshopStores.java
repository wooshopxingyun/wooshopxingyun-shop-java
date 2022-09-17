

package com.wooshop.modules.stores.domain;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import javax.validation.constraints.*;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;




/**
* @author woo
* @date 2021-12-21
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_stores")
public class WooshopStores extends BaseDomain {
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "店铺名称")
    @NotBlank
    private String storesName;

    @ApiModelProperty(value = "店铺信息")
    @NotBlank
    private String storesInfo;

    @ApiModelProperty(value = "店铺联系手机号码")
    @NotBlank
    private String telephone;

    @ApiModelProperty(value = "店铺联系称呼")
    @NotBlank
    private String contactName;

    @ApiModelProperty(value = "省市区")
    @NotBlank
    private String storesAddress;

    @ApiModelProperty(value = "详细地址")
    @NotBlank
    private String detailedAddress;

    @ApiModelProperty(value = "门店logo")
    @NotBlank
    private String storesLogo;

    @ApiModelProperty(value = "店铺地址纬度")
    @NotBlank
    private String latitude;

    @ApiModelProperty(value = "店铺地址经度")
    @NotBlank
    private String longitude;

    @ApiModelProperty(value = "允许核销有效日期")
//    @NotBlank
    private String validTime;

    @ApiModelProperty(value = "营业时间")
    @NotBlank
    private String dayTime;

    @ApiModelProperty(value = "店铺状态:1开启 0隐藏")
    @NotNull
    private Integer isStart;

    @ApiModelProperty(value = "是否允许自提:1允许 0不允许")
    @NotNull
    private Integer isTake;

    @ApiModelProperty(value = "管理用户id")
    private Long uid;

    @ApiModelProperty(value = "0 需要审核 并且待审核，1 不需要审核 2需要审核 且审核通过 3 需要审核 且审核未通过")
    private Integer isAuth;

    @ApiModelProperty(value = "审核信息")
    private String authMessage;

    @ApiModelProperty(value = "排序")
    private Integer sort;


    @ApiModelProperty(value = "省市区")
    @TableField(exist = false)
    private String storesAddressName;



    public void copy(WooshopStores source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
