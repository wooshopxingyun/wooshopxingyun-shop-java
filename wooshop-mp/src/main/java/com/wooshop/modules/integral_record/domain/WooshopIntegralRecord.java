

package com.wooshop.modules.integral_record.domain;

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
* @date 2021-12-19
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_integral_record")
public class WooshopIntegralRecord extends BaseDomain {
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户uid")
    @NotNull
    private Long uid;

    @ApiModelProperty(value = "关联订单id，2签到、系统默认为0")
    @NotBlank
    private String integralId;

    @ApiModelProperty(value = "关联类型: 1订单积分、2签到积分、3系统添加")
    @NotNull
    private Integer integralType;

    @ApiModelProperty(value = "积分类型：1-增加积分，2-扣减积分")
    @NotNull
    private Integer integralRecordType;

    @ApiModelProperty(value = "积分名称")
    @NotBlank
    private String integralTitle;

    @ApiModelProperty(value = "积分")
    @NotNull
    private Integer integral;

    @ApiModelProperty(value = "剩余积分")
    @NotNull
    private Integer beforeIntegral;

    @ApiModelProperty(value = "积分备注")
    @NotBlank
    private String remarks;

    @ApiModelProperty(value = "积分状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款/订单取消）")
    @NotNull
    private Integer isState;

    @ApiModelProperty(value = "积分冻结期时间（天）")
    @NotNull
    private Integer freezeDate;

    @ApiModelProperty(value = "积分解冻时间")
    @NotNull
    private Long thawDate;

    public void copy(WooshopIntegralRecord source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
