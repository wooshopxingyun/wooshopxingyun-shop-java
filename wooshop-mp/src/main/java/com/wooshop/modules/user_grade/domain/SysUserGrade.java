

package com.wooshop.modules.user_grade.domain;

    import java.math.BigDecimal;
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
* @date 2021-12-13
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("sys_user_grade")
public class SysUserGrade extends BaseDomain {
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "等级名称")
    @NotBlank
    private String gradeName;

    @ApiModelProperty(value = "等级权重")
    private Integer gradeWeight;

    @ApiModelProperty(value = "等级条件:满足金额条件")
    private Integer gradeCondition;

    @ApiModelProperty(value = "购买金额|经验达到")
    private Integer experience;

    @ApiModelProperty(value = "等级权益")
    private BigDecimal gradeRights;

    @ApiModelProperty(value = "1启用 0关闭 状态")
    private Integer isStart;


    @ApiModelProperty(value = "等级图标")
    private String gradeIcons;

    @ApiModelProperty(value = "排序 数字越小越靠前")
    private Integer sort;


    public void copy(SysUserGrade source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
