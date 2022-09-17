

package com.wooshop.modules.category.domain;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;

import javax.validation.constraints.*;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("wooshop_config_category")
public class WooshopConfigCategory extends BaseDomain {
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Integer id;

    /** 分类父级ID */
//    @NotNull
    @ApiModelProperty(value = "分类父级id")
    private Integer parentPid;

    /** 分类路径 */
//    @NotBlank
    @ApiModelProperty(value = "分类路径")
    private String path;

    /** 分类名称 */
    @NotBlank
    @ApiModelProperty(value = "分类名称")
    private String name;

    /** 分类类型，1 附件分类，2 文章分类 */
    @ApiModelProperty(value = "分类类型，1 附件分类，2 商品分类, 3 新闻文章分类")
    private Integer type;

    /** 分类地址， */
    @ApiModelProperty(value = "分类地址")
    private String url;

    /** 分类扩展字段  */
    @ApiModelProperty(value = "分类扩展字段")
    private String extra;

    /** 分类状态, 1启用，0失效 */
//    @NotNull
    @ApiModelProperty(value = "分类状态, 1启用，0失效 ")
    private Integer enabled;

    /** 分类排序 */
    @NotNull
    @ApiModelProperty(value = "分类排序")
    private Integer sort;


    @ApiModelProperty(value = "创建人")
    private Long createUid;

    @ApiModelProperty(value = "分类类型.处理前端关键冲突")
    @TableField(exist = false)
    private Integer types;


    public void copy(WooshopConfigCategory source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
