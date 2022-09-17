

package com.wooshop.modules.experience_record.domain;

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
* @date 2022-03-02
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_user_experience_record")
public class WooshopUserExperienceRecord extends BaseDomain {
    @ApiModelProperty(value = "经验记录表主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户uid")
    @NotNull
    private Long uid;

    @ApiModelProperty(value = "关联id-orderNo,(sign,system默认为0）")
    @NotBlank
    private String linkId;

    @ApiModelProperty(value = "关联类型（order,sign,system）")
    @NotBlank
    private String linkType;

    @ApiModelProperty(value = "类型：1-增加，2-扣减")
    @NotNull
    private Integer recordType;

    @ApiModelProperty(value = "标题")
    @NotBlank
    private String recordTitle;

    @ApiModelProperty(value = "经验")
    @NotNull
    private Integer experience;

    @ApiModelProperty(value = "剩余")
    @NotNull
    private Integer balance;

    @ApiModelProperty(value = "备注")
    @NotBlank
    private String mark;

    @ApiModelProperty(value = "状态：1-成功（保留字段）0-失败")
    @NotNull
    private Integer isStatus;





    public void copy(WooshopUserExperienceRecord source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
