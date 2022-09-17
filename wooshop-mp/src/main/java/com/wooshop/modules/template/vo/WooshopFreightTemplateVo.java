

package com.wooshop.modules.template.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;


/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@ApiModel(value = "运费模板")
@Data
public class WooshopFreightTemplateVo implements Serializable {

    @ApiModelProperty(value = "运费模板编号")
    private Long id;

    @ApiModelProperty(value = "运费模板名称")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "运费计费类型：1按体积计费、2按重量计费、3按件数计费")
//    @NotNull
    private Integer ttype;

    @ApiModelProperty(value = "指定包邮项是否开启")
//    @NotNull
    private Integer pinkage;

    @ApiModelProperty(value = "顺序")
//    @NotNull
    private Integer sort;

    @ApiModelProperty(value = "指定区域列表")
    private List<WooshopFreightTemplateAssignVo> assignData;

    @ApiModelProperty(value = "免费区域列表")
    private List<WooshopFreightTemplatePinkageVo> pinkageData;


    @ApiModelProperty(value = "状态, 1启用，0失效  数据库默认1")
    private Integer isStart;

    @ApiModelProperty(value = "//创建人id")
    private Long uid;

    private Long storeId;


    public void copy(WooshopFreightTemplateVo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
