package com.wooshop.modules.user_bill.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;


/**
* @author woo
* @date 2022-02-11
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopUserBillDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "用户uid")
    private Long uid;

    @ApiModelProperty(value = "关联id")
    private String linkId;

    @ApiModelProperty(value = "0 = 支出 1 = 获得")
    private Integer billPm;

    @ApiModelProperty(value = "会员账单标题")
    private String billTitle;

    @ApiModelProperty(value = "账单明细种类")
    private String category;

    @ApiModelProperty(value = "账单明细类型")
    private String billType;

    @ApiModelProperty(value = "账单明细数字")
    private BigDecimal billNumber;

    @ApiModelProperty(value = "账单剩余")
    private BigDecimal balance;

    @ApiModelProperty(value = "账单备注")
    private String mark;

    @ApiModelProperty(value = "0 = 待确定 1 = 有效 -1 = 无效")
    private Integer status;

    @ApiModelProperty(value = "添加时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除: 1表示删除")
    private Integer isDel;
}
