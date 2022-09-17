
package com.wooshop.modules.category.service.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class WooshopConfigCategoryDto implements Serializable {

    private Integer id;

    /** 分类父级ID */
    private Integer parentPid;

    /** 分类路径 */
    private String path;

    /** 分类名称 */
    private String name;

    /** 分类类型，1 附件分类，2 商品分类 , 3 新闻文章分类*/
    private Integer type;

    /** 分类地址， */
    private String url;

    /** 分类扩展字段  */
    private String extra;

    /** 分类状态, 1启用，0失效 */
    private Integer enabled;

    /** 分类排序 */
    private Integer sort;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
//    private Date updateTime;

//    private Long createUid;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    //属性为 空（""）[] 或者为 NULL 都不序列化
    private List<WooshopConfigCategoryDto> childClass = new ArrayList<>();
}
