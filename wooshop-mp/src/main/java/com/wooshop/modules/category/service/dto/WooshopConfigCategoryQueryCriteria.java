

package com.wooshop.modules.category.service.dto;

    import com.baomidou.mybatisplus.annotation.TableField;
    import com.wooshop.common.Query;
import lombok.Data;


/**
* @author woo
* @date 2021-11-15
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooshopConfigCategoryQueryCriteria{

    /** 精确 */
    @Query
    private Integer parentPid;


    /** 精确 */
    @Query
    private Long id;

    /** 精确 */
    @Query
    private String path;

    /** 精确 */
    @Query
    private String name;

    /** 精确 */
    @Query
    private Integer type;

    /** 精确 */
    @TableField(exist = false)
    private Integer types;

    /** 分类状态, 1启用，0失效 */
    private Integer enabled;

}
