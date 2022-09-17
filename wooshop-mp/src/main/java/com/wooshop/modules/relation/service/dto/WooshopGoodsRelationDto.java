package com.wooshop.modules.relation.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


/**
* @author woo
* @date 2022-01-11
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class WooshopGoodsRelationDto implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long uid;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "收藏类型:1商品收藏、2推荐商品、3点赞")
    private Integer relationType;

    @ApiModelProperty(value = "商品类型:1普通商品、2限时抢购商品、3团购商品、4拼团商品")
    private Integer goodsType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除:1表示删除")
    private Integer isDel;


    /**    商品表     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品主图片")
    private String coverImage;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "积分抵扣状态:0不开启、1开启抵扣")
    private Integer isIntegral;

    @ApiModelProperty(value = "谁承担运费：0买家承担，1：卖家承担 默认1")
    private Integer goodsTransfeeCharge;

    @ApiModelProperty(value = "市场价格")
    private BigDecimal mktprice;
}
