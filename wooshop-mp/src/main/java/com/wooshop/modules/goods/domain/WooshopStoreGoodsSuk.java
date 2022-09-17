

package com.wooshop.modules.goods.domain;

    import java.math.BigDecimal;
import cn.hutool.core.bean.copier.CopyOptions;
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




/**
* @author woo
* @date 2021-12-01
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_store_goods_suk")
public class WooshopStoreGoodsSuk extends BaseDomain {
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "属性对应")
    private String specificationValue;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "商品属性索引值")
    private String suk;

    @ApiModelProperty(value = "库存")
    private Integer quantity;

    @ApiModelProperty(value = "销售量")
    private Integer sales;

    @ApiModelProperty(value = "金额")
    private BigDecimal price;

    @ApiModelProperty(value = "规格图片")
    private String sukImg;

    @ApiModelProperty(value = "成本")
    private BigDecimal cost;

    @ApiModelProperty(value = "编码")
    private String goodsCode;

    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "商品重量")
    private BigDecimal weight;

    @ApiModelProperty(value = "商品体积")
    private BigDecimal volume;

    @ApiModelProperty(value = "一级分销佣金")
    private BigDecimal distribution;

    @ApiModelProperty(value = "二级分销佣金")
    private BigDecimal distributionSecond;

    @ApiModelProperty(value = "活动类型 0普通商品 1秒杀 2砍价 3拼团")
    private Integer activityType;

    @ApiModelProperty(value = "活动限购库存数量")
    private Integer restrictions;

    @ApiModelProperty(value = "显示限购的数量")
    private Integer restrictionsShow;

//    @ApiModelProperty(value = "状态")
//    private Integer isStart;

    /*@ApiModelProperty(value = "活动商品id")
    private Long activityId;*/




    public void copy(WooshopStoreGoodsSuk source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
