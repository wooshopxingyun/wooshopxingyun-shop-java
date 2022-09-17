

package com.wooshop.modules.brokerage_record.service.dto;

import com.wooshop.common.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* @author woo
* @date 2022-04-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooshopUserBrokerageRecordQueryCriteria{

    /** 精确 */
    @Query
    @ApiModelProperty(value = "主键id")
    private Long id;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "用户uid (推广人id)")
    private Long uid;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "关联id（orderNo,提现id）")
    private String linkId;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "关联类型（order,extract，yue）")
    private String linkType;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款），5-提现申请")
    private Integer isStart;
}
