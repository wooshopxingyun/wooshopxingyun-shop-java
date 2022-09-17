

package com.wooshop.modules.wechat_reply.domain;

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
* @date 2022-02-23
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_wechat_reply")
public class WooshopWechatReply extends BaseDomain {
    @ApiModelProperty(value = "微信关键字回复id主键")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "关键字")
    @NotBlank
    private String replyKey;

    @ApiModelProperty(value = "回复类型")
    @NotBlank
    private String replyType;

    @ApiModelProperty(value = "回复数据")
    @NotBlank
    private String replyData;

    @ApiModelProperty(value = "0=不可用  1 =可用")
    @NotNull
    private Integer isStatus;

    @ApiModelProperty(value = "是否隐藏")
    private Integer replyHide;





    public void copy(WooshopWechatReply source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
