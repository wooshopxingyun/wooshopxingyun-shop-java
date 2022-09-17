

package com.wooshop.modules.withdraw_record.domain;

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
@TableName("wooshop_withdraw_record")
public class WooshopWithdrawRecord extends BaseDomain {
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "会员id")
    @NotNull
    private Long uid;

    @ApiModelProperty(value = "提款名称")
    private String withdrawName;

    @ApiModelProperty(value = "提现方式:1支付宝,2微信")
    private Integer withdrawType;

    @ApiModelProperty(value = "支付宝账号")
    private String alipayCode;

    @ApiModelProperty(value = "提款微信号")
    private String wechatCode;

    @ApiModelProperty(value = "提款金额")
    private BigDecimal withdrawMoney;

    @ApiModelProperty(value = "备注")
    private String mark;

    @ApiModelProperty(value = "剩余余额")
    private BigDecimal afterBalance;

    @ApiModelProperty(value = "审核情况")
    private String auditMsg;

    @ApiModelProperty(value = "审核状态:0审核中、1已提现、2未通过、3会员撤销 ")
    private Integer isStart;

    @ApiModelProperty(value = "提现失败时间")
    private Date failTime;

    @ApiModelProperty(value = "微信/支付宝收款二维码")
    private String qrCode;


    public void copy(WooshopWithdrawRecord source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
