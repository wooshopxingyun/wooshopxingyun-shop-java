
package com.wooshop.modules.syscity.service.dto;


import java.io.Serializable;
import java.util.Date;

import lombok.Data;





/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
public class WooshopSysCityDto implements Serializable {

    /** 主键id */
    private Long id;

    /** 城市id */
    private Integer cityId;

    /** 省市级别 */
    private Integer cityLevel;

    /** 父级id */
    private Integer parentId;

    /** 区号 */
    private String areaCode;

    /** 名称 */
    private String name;

    /** 合并名称 */
    private String mergerName;

    /** 经度 */
    private String lng;

    /** 纬度 */
    private String lat;

    /** 1展示 0不展示 */
    private Integer isStart;

    /** //国标编码 */
    private String standardCode;

    /** 邮编 */
    private String postcode;

    /** 经纬度 坐标 */
    private String coordinate;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
    /** 合并名称 */
    private String mergersName;
}
