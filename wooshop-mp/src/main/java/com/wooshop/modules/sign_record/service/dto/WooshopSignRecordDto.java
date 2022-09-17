package com.wooshop.modules.sign_record.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2021-12-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopSignRecordDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "用户uid")
    private Long uid;

    @ApiModelProperty(value = "签到说明")
    private String signTitle;

    @ApiModelProperty(value = "获得")
    private Integer gainNumber;

    @ApiModelProperty(value = "剩余")
    private Integer afterBalance;

    @ApiModelProperty(value = "获得类型，1积分，2经验")
    private Integer signType;

    @ApiModelProperty(value = "添加时间/签到时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 1删除 默认0")
    private Integer isDel;

    @ApiModelProperty(value = "创建时间 不含时分秒")
    private Date createDay;
}
