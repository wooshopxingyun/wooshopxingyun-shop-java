

package com.wooshop.modules.goodsEvaluation.domain;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;
import java.util.List;


/**
* @author woo
* @date 2022-01-17
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_goods_evaluation")
public class WooshopGoodsEvaluation extends BaseDomain {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
//    @NotNull
    private Long uid;

    @ApiModelProperty(value = "订单ID")
//    @NotNull
    private Long orderId;

    @ApiModelProperty(value = "商品规格唯一id sukId")
//    @NotBlank
    private String goodsAttrUnique;

    @ApiModelProperty(value = "商品id")
//    @NotNull
    private Long goodsId;

    @ApiModelProperty(value = "商品类型(1普通商品、2秒杀商品、3团购商品、4砍价商品）")
//    @NotNull
    private Integer goodsType;

    @ApiModelProperty(value = "商品分数")
//    @NotNull
    private Integer goodsScore;

    @ApiModelProperty(value = "服务分数")
//    @NotNull
    private Integer serviceScore;

    @ApiModelProperty(value = "评论内容")
//    @NotBlank
    private String comment;

    @ApiModelProperty(value = "评论图片")
//    @NotBlank
    private String evaluationImg;

    @ApiModelProperty(value = "管理员回复内容")
    private String adminReplyContent;

    @ApiModelProperty(value = "管理员回复时间")
    private Date adminReplyTime;


    @ApiModelProperty(value = "0未回复1已回复")
//    @NotNull
    private Integer isReply;

    @ApiModelProperty(value = "用户名称")
//    @NotBlank
    private String nickname;

    @ApiModelProperty(value = "用户头像")
//    @NotBlank
    private String avatar;

    @ApiModelProperty(value = "商品规格属性值,多个,号隔开")
    private String goodsSku;

    @ApiModelProperty(value = "店铺id")
    private Long sellerId;


    @ApiModelProperty(value = "状态（0：不显示，1：显示）")
    private Integer isStart;

    @ApiModelProperty(value = "评价图片列表")
    @TableField(exist = false)
    private List<String> imgPath;


    public void copy(WooshopGoodsEvaluation source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
