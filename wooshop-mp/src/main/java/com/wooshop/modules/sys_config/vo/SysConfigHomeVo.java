package com.wooshop.modules.sys_config.vo;

import com.wooshop.modules.sys_config.domain.WooSysConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysConfigHomeVo implements Serializable {

    @ApiModelProperty(value = "轮播图")
    WooSysConfig swiperImg;

    @ApiModelProperty(value = "主页菜单")
    WooSysConfig menu;

    @ApiModelProperty(value = "优惠图")
    WooSysConfig discounts ;

    @ApiModelProperty(value = "推荐商品")
    WooSysConfig putGoods;

    @ApiModelProperty(value = "广告横幅")
    WooSysConfig advBanner;

    @ApiModelProperty(value = "秒杀活动")
    WooSysConfig seckill;

    @ApiModelProperty(value = "拼团活动")
    WooSysConfig groupBuying;

    @ApiModelProperty(value = "砍价活动")
    WooSysConfig bargain;

    @ApiModelProperty(value = "商品排行榜")
    WooSysConfig topGoods;

    @ApiModelProperty(value = "推荐栏")
    WooSysConfig recom;
}
