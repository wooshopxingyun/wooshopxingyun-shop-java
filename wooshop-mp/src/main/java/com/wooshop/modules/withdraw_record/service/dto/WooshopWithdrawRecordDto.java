package com.wooshop.modules.withdraw_record.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;


/**
* @author woo
* @date 2021-12-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopWithdrawRecordDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "会员id")
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

    @ApiModelProperty(value = "审核状态:0审核中、1已提现、2未通过、3会员撤销")
    private Integer isStart;

    @ApiModelProperty(value = "申请时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 1删除")
    private Integer isDel;

    @ApiModelProperty(value = "提现失败时间")
    private Date failTime;

    @ApiModelProperty(value = "微信/支付宝收款二维码")
    private String qrCode;
}
