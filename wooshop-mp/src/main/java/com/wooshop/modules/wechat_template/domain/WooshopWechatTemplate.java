

package com.wooshop.modules.wechat_template.domain;

import cn.hutool.core.bean.copier.CopyOptions;
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
* @date 2022-02-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_wechat_template")
public class WooshopWechatTemplate extends BaseDomain {
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "模板编号")
    @NotBlank
    private String tempCode;

    @ApiModelProperty(value = "模板名称")
    @NotBlank
    private String tempName;

    @ApiModelProperty(value = "回复内容")
    @NotBlank
    private String tempContent;

    @ApiModelProperty(value = "模板ID")
    private String tempId;



    @ApiModelProperty(value = "状态:0不启用/1启用")
    @NotNull
    private Integer isStatus;


    @ApiModelProperty(value = "类型：temp:模板消息 sub:订阅消息")
    private String tempType;


    public void copy(WooshopWechatTemplate source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
