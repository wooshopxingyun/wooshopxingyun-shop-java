

package com.wooshop.modules.user_bill.service.dto;

import com.wooshop.common.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* @author woo
* @date 2022-02-11
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooshopUserBillQueryCriteria{

    /** 精确 */
    @Query
    @ApiModelProperty(value = "用户uid")
    private Long uid;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "关联id")
    private String linkId;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "0 = 支出 1 = 获得")
    private Integer billPm;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "账单明细种类")
    private String category;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "账单明细类型")
    private String billType;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "0 = 待确定 1 = 有效 -1 = 无效")
    private Integer status;
}
