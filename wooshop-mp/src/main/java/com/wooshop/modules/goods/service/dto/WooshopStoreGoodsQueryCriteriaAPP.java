

package com.wooshop.modules.goods.service.dto;

import com.wooshop.common.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* @author woo
* @date 2021-11-30
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooshopStoreGoodsQueryCriteriaAPP {

    /** 精确 */
    @ApiModelProperty(value = "商品编码")
    @Query
    private String goodsCode;

    /** 精确  状态（0：未上架，1：上架） */
    @Query
    private Integer isStart;

    /** 新品 1是 */
    @Query
    private Integer isNew;

    /** 精确 */
    @Query(type = Query.Type.EQUAL)
    private Long id;

    /** 精确 */
    @Query(type = Query.Type.EQUAL)
    private Integer isDel;


    @ApiModelProperty(value = "状态类型,非数据库字段")
    private Integer goodsStartType;

    @ApiModelProperty(value = "商品名称")
    @Query(type = Query.Type.INNER_LIKE)
    private String goodsName;


    @ApiModelProperty(value = "商品分类id")
    @Query(type = Query.Type.INNER_LIKE)
    private String categoryId;

}
