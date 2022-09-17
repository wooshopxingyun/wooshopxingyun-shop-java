

package com.wooshop.modules.goodsEvaluation.service.dto;

import com.wooshop.common.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* @author woo
* @date 2022-01-17
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooshopGoodsEvaluationQueryCriteria{

    /** 精确 */
    @Query
    @ApiModelProperty(value = "用户ID")
    private Long uid;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "商品类型(1普通商品、2秒杀商品、3团购商品、4砍价商品）")
    private Integer goodsType;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "店铺ID")
    private Long sellerId;
}
