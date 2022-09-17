

package com.wooshop.modules.brokerage_record.domain;

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
* @date 2022-04-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_user_brokerage_record")
public class WooshopUserBrokerageRecord extends BaseDomain {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户uid (推广人id)")
    @NotNull
    private Long uid;

    @ApiModelProperty(value = "关联id（orderNo,提现id）")
    @NotBlank
    private String linkId;

    @ApiModelProperty(value = "关联类型（order,extract，yue）")
    @NotBlank
    private String linkType;

    @ApiModelProperty(value = "类型：1-增加，2-扣减（提现）")
    @NotNull
    private Integer broType;

    @ApiModelProperty(value = "标题")
    @NotBlank
    private String broTitle;

    @ApiModelProperty(value = "金额")
    @NotNull
    private BigDecimal broPrice;

    @ApiModelProperty(value = "剩余")
    @NotNull
    private BigDecimal broBalance;

    @ApiModelProperty(value = "备注")
    @NotBlank
    private String broMark;

    @ApiModelProperty(value = "状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款），5-提现申请")
    @NotNull
    private Integer isStart;

    @ApiModelProperty(value = "冻结期时间（天）")
    @NotNull
    private Integer frozenTime;

    @ApiModelProperty(value = "解冻时间")
    @NotNull
    private Long thawTime;



    @ApiModelProperty(value = "分销等级")
    private Integer brokerageLevel;



    public void copy(WooshopUserBrokerageRecord source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
