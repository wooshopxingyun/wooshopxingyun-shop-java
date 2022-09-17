

package com.wooshop.modules.money_record.domain;

import java.math.BigDecimal;
import java.util.Date;

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
@TableName("wooshop_money_record")
public class WooshopMoneyRecord extends BaseDomain {
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @NotNull
    private Long uid;

    @ApiModelProperty(value = "充值订单id")
    @NotBlank
    private String orderId;

    @ApiModelProperty(value = "充值金额")
    @NotNull
    private BigDecimal money;

    @ApiModelProperty(value = "赠送金额")
    private BigDecimal giveMoney;

    @ApiModelProperty(value = "充值类型")
    private String moneyType;

    @ApiModelProperty(value = "是否充值金额")
    private Integer isPaid;

    @ApiModelProperty(value = "充值金额支付时间")
    private Date payTime;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundMoney;


    public void copy(WooshopMoneyRecord source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
