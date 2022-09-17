package com.wooshop.modules.sys_attachment.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;


/**
* @author woo
* @date 2022-06-02
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopSystemAttachmentDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "附件名称")
    private String attName;

    @ApiModelProperty(value = "附件路径")
    private String attDir;

    @ApiModelProperty(value = "压缩图片路径")
    private String sattDir;

    @ApiModelProperty(value = "附件大小")
    private String attSize;

    @ApiModelProperty(value = "附件类型")
    private String attType;

    @ApiModelProperty(value = "分类ID0编辑器,1产品图片,2拼团图片,3砍价图片,4秒杀图片,5文章图片,6组合数据图")
    private Integer attPid;

    @ApiModelProperty(value = "图片上传类型 1本地 2七牛云 3OSS 4COS ")
    private Integer imageType;

    @ApiModelProperty(value = "图片上传模块类型 1 后台上传 2 用户生成")
    private Integer moduleType;

    @ApiModelProperty(value = "用户id")
    private Long uid;

    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

    private Date createTime;

    private Date updateTime;

    private Integer isDel;
}
