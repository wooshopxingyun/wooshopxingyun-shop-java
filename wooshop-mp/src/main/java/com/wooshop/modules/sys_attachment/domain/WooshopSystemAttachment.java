

package com.wooshop.modules.sys_attachment.domain;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import javax.validation.constraints.*;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;




/**
* @author woo
* @date 2022-06-02
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_system_attachment")
public class WooshopSystemAttachment extends BaseDomain {
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "附件名称")
    @NotBlank
    private String attName;

    @ApiModelProperty(value = "附件路径")
    @NotBlank
    private String attDir;

    @ApiModelProperty(value = "压缩图片路径")
    private String sattDir;

    @ApiModelProperty(value = "附件大小")
    @NotBlank
    private String attSize;

    @ApiModelProperty(value = "附件类型")
    @NotBlank
    private String attType;

    @ApiModelProperty(value = "分类ID0编辑器,1产品图片,2拼团图片,3砍价图片,4秒杀图片,5文章图片,6组合数据图")
    @NotNull
    private Integer attPid;

    @ApiModelProperty(value = "图片上传类型 1本地 2七牛云 3OSS 4COS ")
    @NotNull
    private Integer imageType;

    @ApiModelProperty(value = "图片上传模块类型 1 后台上传 2 用户生成")
    @NotNull
    private Integer moduleType;

    @ApiModelProperty(value = "用户id")
    private Long uid;

    @ApiModelProperty(value = "邀请码")
    private String inviteCode;





    public void copy(WooshopSystemAttachment source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
