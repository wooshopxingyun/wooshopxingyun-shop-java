

package com.wooshop.modules.template.domain;

import cn.hutool.core.bean.copier.CopyOptions;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;

import javax.validation.constraints.*;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.util.List;


/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
@TableName("wooshop_freight_template")
public class WooshopFreightTemplate extends BaseDomain {
    @ApiModelProperty(value = "运费模板编号")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "运费模板名称")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "运费计费类型：1按体积计费、2按重量计费、3按件数计费")
    @NotNull
    private Integer ttype;

    @ApiModelProperty(value = "指定包邮项是否开启")
    @NotNull
    private Integer pinkage;

    @ApiModelProperty(value = "顺序")
    @NotNull
    private Integer sort;

    @ApiModelProperty(value = "状态, 1启用，0失效  数据库默认1")
    private Integer isStart;

    @ApiModelProperty(value = "//创建人id")
    private Long uid;

    private Long storeId;


    @ApiModelProperty(value = "指定区域列表")
    @TableField(exist = false)
    private List<WooshopFreightTemplateAssign> assignData;

    @ApiModelProperty(value = "免费区域列表")
    @TableField(exist = false)
    private List<WooshopFreightTemplatePinkage> pinkageData;


    public void copy(WooshopFreightTemplate source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
