

package com.wooshop.modules.user_level.domain;

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

import java.sql.Timestamp;


/**
* @author woo
* @date 2022-04-16
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_user_level")
public class WooshopUserLevel extends BaseDomain {

    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户uid")
    @NotNull
    private Long uid;

    @ApiModelProperty(value = "等级vip")
    @NotNull
    private Integer levelId;

    @ApiModelProperty(value = "会员等级")
    @NotNull
    private Integer grade;

    @ApiModelProperty(value = "0:禁止,1:正常")
    @NotNull
    private Integer isStart;

    @ApiModelProperty(value = "备注")
    @NotBlank
    private String mark;

    @ApiModelProperty(value = "是否已通知")
    @NotNull
    private Integer remind;

    @ApiModelProperty(value = "享受折扣")
    @NotNull
    private Integer discount;

    @ApiModelProperty(value = "过期时间")
    private Timestamp expiredTime;


    public void copy(WooshopUserLevel source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
