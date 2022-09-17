

package com.wooshop.modules.sign_record.domain;

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

import java.util.Date;


/**
* @author woo
* @date 2021-12-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_sign_record")
public class WooshopSignRecord extends BaseDomain {
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户uid")
    @NotNull
    private Long uid;

    @ApiModelProperty(value = "签到说明")
    @NotBlank
    private String signTitle;

    @ApiModelProperty(value = "获得")
    @NotNull
    private Integer gainNumber;

    @ApiModelProperty(value = "剩余")
    @NotNull
    private Integer afterBalance;

    @ApiModelProperty(value = "获得类型，1积分，2经验")
    @NotNull
    private Integer signType;

    @ApiModelProperty(value = "创建时间 不含时分秒")
    private Date createDay;





    public void copy(WooshopSignRecord source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
