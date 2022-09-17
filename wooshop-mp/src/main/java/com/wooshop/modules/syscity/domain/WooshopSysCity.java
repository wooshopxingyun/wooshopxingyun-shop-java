

package com.wooshop.modules.syscity.domain;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;

import javax.validation.constraints.*;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("wooshop_sys_city")
public class WooshopSysCity extends BaseDomain {
    /** 主键id */
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    /** 城市id */
    @NotNull
    private Integer cityId;

    /** 省市级别 */
    @NotNull
    private Integer cityLevel;

    /** 父级id */
    @NotNull
    private Integer parentId;

    /** 区号 */
    @NotBlank
    private String areaCode;

    /** 名称 */
    @NotBlank
    private String name;

    /** 合并名称 */
    @NotBlank
    private String mergerName;

    /** 经度 */
    @NotBlank
    private String lng;

    /** 纬度 */
    @NotBlank
    private String lat;

    /** 1展示 0不展示 */
    @NotNull
    private Integer isStart;

    /** //国标编码 */
    private String standardCode;

    /** 邮编 */
    private String postcode;

    /** 经纬度 坐标 */
    private String coordinate;

    /** 合并名称 */
    private String mergersName;



    public void copy(WooshopSysCity source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
