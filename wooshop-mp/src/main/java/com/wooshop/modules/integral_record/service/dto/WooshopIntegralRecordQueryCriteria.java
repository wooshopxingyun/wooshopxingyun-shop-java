

package com.wooshop.modules.integral_record.service.dto;

import com.wooshop.common.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* @author woo
* @date 2021-12-19
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooshopIntegralRecordQueryCriteria{

    /** 精确 */
    @Query
    @ApiModelProperty(value = "关联类型: 1订单积分、2签到积分、3系统添加")
    private Integer integralType;
    /** 精确 */
    @Query
    @ApiModelProperty(value = "用户uid")
    private Long uid;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    @ApiModelProperty(value = "积分名称")
    private String integralTitle;
}
