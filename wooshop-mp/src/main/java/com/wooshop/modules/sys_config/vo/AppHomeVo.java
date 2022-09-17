package com.wooshop.modules.sys_config.vo;

import com.wooshop.modules.goods.service.dto.WooshopStoreGoodsDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AppHomeVo implements Serializable {

    @ApiModelProperty(value = "主页菜单")
    SysConfigHomeVo homeMenu;

    @ApiModelProperty(value = "普通商品排行榜")
    List<WooshopStoreGoodsDto>   storeGoodsDtoList;

}
