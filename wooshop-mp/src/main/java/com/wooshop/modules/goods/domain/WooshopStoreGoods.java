

package com.wooshop.modules.goods.domain;

    import java.math.BigDecimal;
    import java.util.List;

    import cn.hutool.core.bean.copier.CopyOptions;
    import com.baomidou.mybatisplus.annotation.TableField;
    import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;
    import io.swagger.annotations.ApiModel;
    import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import javax.validation.constraints.*;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;




/**
* @author woo
* @date 2021-11-30
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_store_goods")
@ApiModel(value = "普通商品对象",description = "普通商品实体对象")
public class WooshopStoreGoods extends BaseDomain {
    @ApiModelProperty(value = "商品主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品介绍")
    private String goodsInfo;

    @ApiModelProperty(value = "商品名称")
    @NotBlank
    private String goodsName;

    @ApiModelProperty(value = "商品主图片")
    private String coverImage;

    @ApiModelProperty(value = "seo关键字")
    private String metaKeywords;

    @ApiModelProperty(value = "商品条码（一维码）")
    private String barCode;

    @ApiModelProperty(value = "商品分类id")
    @NotBlank
    private String categoryId;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "市场价格")
    private BigDecimal mktprice;

    @ApiModelProperty(value = "谁承担运费：0买家承担，1：卖家承担 默认1")
    private Integer goodsTransfeeCharge;

    @ApiModelProperty(value = "计量单位")
    private String goodsUnit;

    @ApiModelProperty(value = "购买数量")
    private Integer buyCount;

    @ApiModelProperty(value = "总的库存数量")
    private Integer quantity;

    @ApiModelProperty(value = "是否促销单品  1是")
    private Integer isBenefit;

    @ApiModelProperty(value = "是否热卖 1热卖")
    private Integer isHot;

    @ApiModelProperty(value = "是否精品 1是")
    private Integer isBest;

    @ApiModelProperty(value = "是否新品 1是")
    private Integer isNew;

    @ApiModelProperty(value = "购买商品获得积分")
    private Integer giveIntegral;

    @ApiModelProperty(value = "积分抵扣状态:0不开启、1开启抵扣")
    private Integer isIntegral;

    @ApiModelProperty(value = "成本价格")
    private BigDecimal cost;

    @ApiModelProperty(value = "秒杀状态 0未开启 1已开启")
    private Integer seckillStart;

    @ApiModelProperty(value = "砍价状态 0未开启 1开启")
    private Integer bargainStart;

    @ApiModelProperty(value = "是否优质商品推荐 0否 1是")
    private Integer goodStart;

    @ApiModelProperty(value = "是否单独分销佣金 1独立 0非独立 默认0")
    private Integer distributionStart;

    @ApiModelProperty(value = "虚拟销量")
    private Integer fictitiousVolume;

    @ApiModelProperty(value = "浏览数量")
    private Integer viewCount;

    @ApiModelProperty(value = "商品二维码地址(用户小程序海报)")
    private String codePath;

    @ApiModelProperty(value = "运费模板id")
    private Long templateId;

    @ApiModelProperty(value = "规格类型 0单规格 1多规格 默认0")
    @NotNull
    private Integer specType;

    @ApiModelProperty(value = "是否是自营商品 0 不是 1是  默认1")
    private Integer selfOperated;

    @ApiModelProperty(value = "0 需要审核 并且待审核，1 不需要审核 2需要审核 且审核通过 3 需要审核 且审核未通过  默认1")
    private Integer isAuth;

    @ApiModelProperty(value = "审核信息")
    private String authMessage;

    @ApiModelProperty(value = "下架原因")
    private String underMessage;

    @ApiModelProperty(value = "评论数量")
    private Integer commentNum;

    @ApiModelProperty(value = "商品好评率")
    private BigDecimal grade;

    @ApiModelProperty(value = "服务承诺")
    private String promiseId;

    @ApiModelProperty(value = "店铺id")
    private Long sellerId;

    @ApiModelProperty(value = "创建人")
    private Long uid;

    @ApiModelProperty(value = "商品排序")
    @NotNull
    private Integer sort;

    @ApiModelProperty(value = "商品编码")
    private String goodsCode;

    @ApiModelProperty(value = "状态（0：未上架，1：上架）")
    private Integer isStart;

    @ApiModelProperty(value = "轮播图")
    private String slideshow;

    @ApiModelProperty(value = "商品详情")//
    private String intro;


    @ApiModelProperty(value = "规格")
    @TableField(exist = false)
    private List<WooshopStoreGoodsSuk> specTypeListData;


    @TableField(exist = false)
    @ApiModelProperty(value = "活动显示排序 0普通商品 1秒杀 2砍价 3拼团")
    private Integer activityType;

    @ApiModelProperty(value = "删除")
    private Integer isDel;

    @ApiModelProperty(value = "规格参数")
    @TableField(exist = false)
    private String sprcificationParlams;

    @ApiModelProperty(value = "规格原始数据")
    @TableField(exist = false)
    private String specTypeListDataOrig;

    @ApiModelProperty(value = "属性表详情")
    @TableField(exist = false)
    private  Long attrDetailsId;


    public void copy(WooshopStoreGoods source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

}
