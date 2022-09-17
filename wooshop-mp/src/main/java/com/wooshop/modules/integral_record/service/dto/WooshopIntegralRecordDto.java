package com.wooshop.modules.integral_record.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2021-12-19
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopIntegralRecordDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "用户uid")
    private Long uid;

    @ApiModelProperty(value = "关联订单id，2签到、3系统默认为0")
    private String integralId;

    @ApiModelProperty(value = "关联类型: 1订单积分、2签到积分、3系统添加")
    private Integer integralType;

    @ApiModelProperty(value = "积分类型：1-增加积分，2-扣减积分")
    private Integer integralRecordType;

    @ApiModelProperty(value = "积分名称")
    private String integralTitle;

    @ApiModelProperty(value = "积分")
    private Integer integral;

    @ApiModelProperty(value = "剩余积分")
    private Integer beforeIntegral;

    @ApiModelProperty(value = "积分备注")
    private String remarks;

    @ApiModelProperty(value = "积分状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款）")
    private Integer isState;

    @ApiModelProperty(value = "积分冻结期时间（天）")
    private Integer freezeDate;

    @ApiModelProperty(value = "积分解冻时间")
    private Long thawDate;

    @ApiModelProperty(value = "添加时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDel;
}
