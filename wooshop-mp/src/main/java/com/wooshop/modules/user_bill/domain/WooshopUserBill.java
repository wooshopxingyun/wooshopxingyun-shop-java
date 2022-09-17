

package com.wooshop.modules.user_bill.domain;

    import java.math.BigDecimal;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;
    import io.swagger.annotations.ApiModel;
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
* @date 2022-02-11
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

/**
 * 包括 获取/使用积分 充值金额 购买商品
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wooshop_user_bill")
@ApiModel(value = "用户账单记录对象")
public class WooshopUserBill extends BaseDomain {
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户uid")
    @NotNull
    private Long uid;

    @ApiModelProperty(value = "关联id")
    @NotBlank
    private String linkId;

    @ApiModelProperty(value = "账单记录:0支出 1获得")
    @NotNull
    private Integer billPm;

    @ApiModelProperty(value = "会员账单标题")
    @NotBlank
    private String billTitle;

    @ApiModelProperty(value = "账单明细种类")
    @NotBlank
    private String category;

    @ApiModelProperty(value = "账单明细类型")
    @NotBlank
    private String billType;

    @ApiModelProperty(value = "账单明细数字")
    @NotNull
    private BigDecimal billNumber;

    @ApiModelProperty(value = "账单剩余")
    @NotNull
    private BigDecimal balance;

    @ApiModelProperty(value = "账单备注")
    @NotBlank
    private String mark;

    @ApiModelProperty(value = "账单状态:0待确定、1有效、 -1无效")
    @NotNull
    private Integer status;





    public void copy(WooshopUserBill source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
