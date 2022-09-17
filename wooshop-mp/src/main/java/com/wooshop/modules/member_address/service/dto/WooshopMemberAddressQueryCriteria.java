

package com.wooshop.modules.member_address.service.dto;

import com.wooshop.common.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* @author woo
* @date 2021-12-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooshopMemberAddressQueryCriteria{



    @ApiModelProperty(value = "主键id")
    /** 精确 */
    @Query
    private Long id;

    @ApiModelProperty(value = "主键id")
    /** 精确 */
    @Query
    private Long uid;

    @ApiModelProperty(value = "姓名")
    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String memberName;


    @ApiModelProperty(value = "电话")
    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String memberMobile;

    @ApiModelProperty(value = "是否为默认收货地址")
    /** 精确 */
    @Query
    private Integer isDefault;
}
