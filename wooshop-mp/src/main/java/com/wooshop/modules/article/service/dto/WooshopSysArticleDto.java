package com.wooshop.modules.article.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2021-12-19
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopSysArticleDto implements Serializable {

    @ApiModelProperty(value = "文章管理ID")
    private Long id;

    @ApiModelProperty(value = "分类id")
    private Integer categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "文章标题")
    private String articleTitel;

    @ApiModelProperty(value = "文章作者")
    private String articleAuthor;

    @ApiModelProperty(value = "文章图片")
    private String coverImage;

    @ApiModelProperty(value = "文章简介")
    private String synopsis;

    @ApiModelProperty(value = "文章分享标题")
    private String shareTitle;

    @ApiModelProperty(value = "文章分享简介")
    private String shareSynopsis;

    @ApiModelProperty(value = "浏览次数")
    private Integer visitCount;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "原文链接")
    private String url;

    @ApiModelProperty(value = "微信素材id")
    private String mediaId;

    @ApiModelProperty(value = "状态 1启用 0关闭")
    private Integer isStart;

    @ApiModelProperty(value = "是否隐藏 0否")
    private Integer hide;

    @ApiModelProperty(value = "管理员id")
    private Integer adminId;

    @ApiModelProperty(value = "商户id")
    private Integer merId;

    @ApiModelProperty(value = "商品关联id")
    private Integer goodsId;

    @ApiModelProperty(value = "是否热门(小程序)")
    private Integer isHot;

    @ApiModelProperty(value = "是否轮播图(小程序)")
    private Integer isBanner;

    @ApiModelProperty(value = "文章内容")
    private String articleContent;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 1表示删除")
    private Integer isDel;

    @ApiModelProperty(value = "点赞数")
    private Integer likeNum;
}
