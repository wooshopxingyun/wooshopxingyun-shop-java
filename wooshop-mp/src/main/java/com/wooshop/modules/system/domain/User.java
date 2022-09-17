package com.wooshop.modules.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.wooshop.base.CommonEntity;
import com.wooshop.dto.WechatUserDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* @author jinjin
* @date 2020-09-25
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class User extends CommonEntity<User> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "user_id", type= IdType.AUTO)
    @NotNull(groups = Update.class)
    private Long id;

    @ApiModelProperty(value = "部门名称")
    private Long deptId;

    @ApiModelProperty(value = "用户名")
    @NotBlank
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "性别 1：男，0：女")
    private String sex;

    @ApiModelProperty(value = "手机号码")
    @NotBlank
    private String phone;

    @ApiModelProperty(value = "邮箱")
//    @NotBlank
    private String email;

    @ApiModelProperty(value = "头像地址")
    private String avatarName;

    @ApiModelProperty(value = "头像真实路径")
    private String avatarPath;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "是否为admin账号")
    private Boolean isAdmin;

    @ApiModelProperty(value = "状态：1启用true、0禁用false")
    private Boolean enabled;

    @ApiModelProperty(value = "修改密码的时间")
    private Date pwdResetTime;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "生日")
    private Integer birthday;

    @ApiModelProperty(value = "身份证号码")
    private String cardId;


    @ApiModelProperty(value = "用户备注")
    private String mark;


    @ApiModelProperty(value = "注册ip")
    private String create_ip;



    @ApiModelProperty(value = "最后一次登录ip")
    private String lastIp;


    @ApiModelProperty(value = "最后一次登录时间")
    private Date loginupdateTime;


    @ApiModelProperty(value = "用户余额")
    private BigDecimal nowMoney;


    @ApiModelProperty(value = "佣金金额")
    private BigDecimal brokeragePrice;


    @ApiModelProperty(value = "用户剩余积分")
    private BigDecimal integral;

    @ApiModelProperty(value = "用户剩余经验")
    private Integer experience;

    @ApiModelProperty(value = "连续签到天数")
    private Integer signNum;


    @ApiModelProperty(value = "等级")
    private Integer level;


    @ApiModelProperty(value = "推广员id")
    private Long spreadUid;


    @ApiModelProperty(value = "推广员关联时间")
    private Date spreadTime;


    @ApiModelProperty(value = "用户类型")
    private String userType;


    @ApiModelProperty(value = "是否为推广员")
    private Integer isPromoter;


    @ApiModelProperty(value = "用户购买次数")
    private Integer payCount;


    @ApiModelProperty(value = "下级人数")
    private Integer spreadCount;


    @ApiModelProperty(value = "管理员编号")
    private Integer adminid;



    @ApiModelProperty(value = "用户登陆类型，h5,wechat,routine")
    private String loginType;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private WechatUserDto wxProfile;


    @ApiModelProperty(value = "店铺ID")
    private Integer shopId;


    @ApiModelProperty(value = "是否开通店铺")
    private Integer haveShop;


    @ApiModelProperty(value = "登陆次数")
    private Integer loginCount;


    @ApiModelProperty(value = "邮件是否已验证")
    private Integer isCheked;


    @ApiModelProperty(value = "会员信息是否完善")
    private Integer infoFull;


    @ApiModelProperty(value = "小程序用户授权id")
    private String openid;




    public <T> void copyFrom(T source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
