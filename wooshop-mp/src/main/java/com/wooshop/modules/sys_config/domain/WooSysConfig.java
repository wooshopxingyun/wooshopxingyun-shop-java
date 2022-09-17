

package com.wooshop.modules.sys_config.domain;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


/**
* @author woo
* @date 2021-11-09
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("woo_sys_config")
public class WooSysConfig extends BaseDomain {
    /** 主键id */
    @TableField(value = "id")
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    /** 名称 menu*/
//    @TableField(value = "menu_name")
    private String menuName;

    /** 值 */
    private String value;

    /** 状态：1启用、0禁用 */
    private Integer enabled;

    /** 排序 数字越小越靠前 */
    private Integer sort;


//    @TableField(exist = false)
//    private MultipartFile file;


    public void copy(WooSysConfig source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
