package com.wooshop.modules.wechat_reply.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2022-02-23
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopWechatReplyDto implements Serializable {

    @ApiModelProperty(value = "微信关键字回复id主键")
    private Long id;

    @ApiModelProperty(value = "关键字")
    private String replyKey;

    @ApiModelProperty(value = "回复类型")
    private String replyType;

    @ApiModelProperty(value = "回复数据")
    private String replyData;

    @ApiModelProperty(value = "0=不可用  1 =可用")
    private Integer isStatus;

    @ApiModelProperty(value = "是否隐藏")
    private Integer replyHide;

    @ApiModelProperty(value = "逻辑删除:1删除")
    private Integer isDel;

    private Date createTime;

    private Date updateTime;
}
