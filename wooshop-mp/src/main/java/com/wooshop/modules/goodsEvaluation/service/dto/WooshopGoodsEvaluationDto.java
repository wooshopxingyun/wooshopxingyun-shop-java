package com.wooshop.modules.goodsEvaluation.service.dto;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2022-01-17
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopGoodsEvaluationDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long uid;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "商品规格唯一id")
    private String goodsAttrUnique;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "商品类型(1普通商品、2秒杀商品、3团购商品、4砍价商品）")
    private Integer goodsType;

    @ApiModelProperty(value = "商品分数")
    private Integer goodsScore;

    @ApiModelProperty(value = "服务分数")
    private Integer serviceScore;

    @ApiModelProperty(value = "评论内容")
    private String comment;

    @ApiModelProperty(value = "评论图片")
    private String evaluationImg;

    @ApiModelProperty(value = "管理员回复内容")
    private String adminReplyContent;

    @ApiModelProperty(value = "管理员回复时间")
    private Date adminReplyTime;

    @ApiModelProperty(value = "逻辑删除:0未删除1已删除")
    private Integer isDel;

    @ApiModelProperty(value = "0未回复1已回复")
    private Integer isReply;

    @ApiModelProperty(value = "用户名称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "商品规格属性值,多个,号隔开")
    private String goodsSku;

    @ApiModelProperty(value = "店铺id")
    private Long sellerId;

    @ApiModelProperty(value = "状态（0：不显示，1：显示）")
    private Integer isStart;


    @ApiModelProperty(value = "评价图片列表")
    @TableField(exist = false)
    private List<String> imgPath;
}
