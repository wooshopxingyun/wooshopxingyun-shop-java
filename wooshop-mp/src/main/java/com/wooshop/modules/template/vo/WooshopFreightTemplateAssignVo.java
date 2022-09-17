

package com.wooshop.modules.template.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@ApiModel(value = "指定区域运费")
@Data
public class WooshopFreightTemplateAssignVo implements Serializable {

    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "模板ID")
//    @NotNull
    private Long templateId;

    @ApiModelProperty(value = "城市ID")
//    @NotNull
    private Long cityId;

    @ApiModelProperty(value = "城市id和城市表父类id")
    private String area;

    @ApiModelProperty(value = "首件商品")
//    @NotNull
    private BigDecimal firstPart;

    @ApiModelProperty(value = "首件运费")
//    @NotNull
    private BigDecimal firstMoney;

    @ApiModelProperty(value = "续件")
//    @NotNull
    private BigDecimal renewal;

    @ApiModelProperty(value = "续件运费")
//    @NotNull
    private BigDecimal renewalMoney;

    @ApiModelProperty(value = "运费计费类型：1按体积计费、2按重量计费、3按件数计费")
    private Integer type;

    @ApiModelProperty(value = "分组唯一值")
//    @NotBlank
    private String pinkageUuid;

    @ApiModelProperty(value = "是否生效")
    private Integer isStart;


    /*@ApiModelProperty(value = "选中的城市列表")
    private String assignCytiList;*/


    public void copy(WooshopFreightTemplateAssignVo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
