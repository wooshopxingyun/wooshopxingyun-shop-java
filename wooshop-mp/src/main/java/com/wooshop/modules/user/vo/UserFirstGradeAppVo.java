package com.wooshop.modules.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserFirstGradeAppVo对象", description="推广人统计")
public class UserFirstGradeAppVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "累计推广人数")
    private Integer userTotal = 0;

    @ApiModelProperty(value = "一级推广人数")
    private Integer firstGradeTotal=0;

    @ApiModelProperty(value = "二级推广人数")
    private Integer secondLevelTotal=0;

}
