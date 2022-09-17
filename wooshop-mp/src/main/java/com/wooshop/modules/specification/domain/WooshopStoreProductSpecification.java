

package com.wooshop.modules.specification.domain;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;

import javax.validation.constraints.*;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
* @author woo
* @date 2021-11-25
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_store_product_specification")
public class WooshopStoreProductSpecification extends BaseDomain {
    @ApiModelProperty(value = "主键")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "规格名称")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "规格值")
    @NotBlank
    private String params;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "开启状态：1开启、0关闭")
    private Integer isStart;

    @ApiModelProperty(value = "创建人")
    private Long uid;

    private Long storeId;

    @ApiModelProperty(value = "单位名称")
    private String unitName;


    public void copy(WooshopStoreProductSpecification source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
