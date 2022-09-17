

package com.wooshop.modules.syscity.service.dto;

    import com.wooshop.common.Query;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;

    import javax.validation.constraints.DecimalMin;


/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooshopSysCityQueryCriteria{

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String mergerName;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String postcode;

    @ApiModelProperty(value = "父级id", required = true, example= "0")
    @DecimalMin(value = "0", message = "父级id必须大于等于0") //数字最小值为0
    private Integer parentId=0;

    //是否展示的
    private Integer isStart=1;
}
