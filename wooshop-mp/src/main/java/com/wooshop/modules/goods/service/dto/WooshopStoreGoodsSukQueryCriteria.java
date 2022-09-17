

package com.wooshop.modules.goods.service.dto;

import com.wooshop.common.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* @author woo
* @date 2021-12-01
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooshopStoreGoodsSukQueryCriteria{



    /** 精确 */
    @Query
    private Long id;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    /** 精确 */
    @ApiModelProperty(value = "活动类型 0普通商品 1秒杀 2砍价 3拼团")
    @Query
    private Integer activityType;

    /** 精确 */
    /*@Query
    @ApiModelProperty(value = "活动商品id")
    private Long activityId;;*/
}
