package com.wooshop.modules.user_grade.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;


/**
* @author woo
* @date 2021-12-13
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class SysUserGradeDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "等级名称")
    private String gradeName;

    @ApiModelProperty(value = "等级权重")
    private Integer gradeWeight;

    @ApiModelProperty(value = "等级条件:满足金额条件")
    private Integer gradeCondition;

    @ApiModelProperty(value = "购买金额|经验达到")
    private Integer experience;

    @ApiModelProperty(value = "等级权益")
    private BigDecimal gradeRights;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "1启用 0关闭 状态")
    private Integer isStart;

    @ApiModelProperty(value = "逻辑删除 1表示删除")
    private Integer isDel;

    @ApiModelProperty(value = "等级图标")
    private String gradeIcons;

    @ApiModelProperty(value = "排序 数字越小越靠前")
    private Integer sort;

    @ApiModelProperty(value = "下一级需要的经验")
    private Integer next_exp_num;
}
