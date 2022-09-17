

package com.wooshop.modules.promise.domain;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;

import javax.validation.constraints.*;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;




/**
* @author woo
* @date 2021-11-25
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
@TableName("wooshop_store_product_promise")
public class WooshopStoreProductPromise extends BaseDomain {
    @ApiModelProperty(value = "主键")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "服务名称")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "概述")
    @NotBlank
    private String overview;

    @ApiModelProperty(value = "状态：1开启、0关闭")
    @NotNull
    private Integer isStart;

    @ApiModelProperty(value = "创建人")
    private Long uid;

    private Long storeId;

    @ApiModelProperty(value = "排序")
    private Integer sort;


    public void copy(WooshopStoreProductPromise source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
