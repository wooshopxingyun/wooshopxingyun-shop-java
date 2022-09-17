

package com.wooshop.modules.relation.service.dto;

import com.wooshop.common.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* @author woo
* @date 2022-01-11
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooshopGoodsRelationQueryCriteria{

    /** 精确 */
    @Query
    @ApiModelProperty(value = "用户id")
    private Long uid;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "收藏类型:1商品收藏、2推荐商品、3点赞")
    private Integer relationType;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "商品类型:1普通商品、2限时抢购商品、3团购商品、4拼团商品")
    private Integer goodsType;
}
