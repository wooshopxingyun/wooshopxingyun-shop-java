
package com.wooshop.modules.sys_config.service.dto;


import java.io.Serializable;
import lombok.Data;





/**
* @author woo
* @date 2021-11-09
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooSysConfigDto implements Serializable {

    /** 主键id */
    private Integer id;

    /** 名称 */
    private String menuName;

    /** 值 */
    private String value;

    /** 状态：1启用、0禁用 */
    private Integer enabled;

    /** 排序 数字越小越靠前 */
    private Integer sort;


}
