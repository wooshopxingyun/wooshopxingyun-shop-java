package com.wooshop.modules.system.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.wooshop.base.CommonDto;
import com.wooshop.dto.DeptSmallDto;
import com.wooshop.dto.JobSmallDto;
import com.wooshop.dto.RoleSmallDto;
import com.wooshop.dto.WechatUserDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author jinjin
 * @date 2020-09-25
 */
@Data
public class UserDto extends CommonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户关联角色")
    private Set<RoleSmallDto> roles;

    @ApiModelProperty(value = "用户关联工作岗位")
    private Set<JobSmallDto> jobs;

    @ApiModelProperty(value = "用户关联部门")
    private DeptSmallDto dept;

    @ApiModelProperty(value = "部门id")
    private Long deptId;

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "性别 男1 女0")
    private String sex;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像 地址")
    private String avatarName;

    @ApiModelProperty(value = "头像真实地址")
    private String avatarPath;

    @ApiModelProperty(value = "密码")
    @JSONField(serialize = false)
    private String password;

    @ApiModelProperty(value = "是否为admin账号")
    @JSONField(serialize = false)
    private Boolean isAdmin;

    @ApiModelProperty(value = "状态：1启用true、0禁用false")
    private Boolean enabled;

    @ApiModelProperty(value = "修改密码时间")
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

    @ApiModelProperty(value = "佣金余额")
    private BigDecimal brokeragePrice;

    @ApiModelProperty(value = "用户积分")
    private BigDecimal integral;

    @ApiModelProperty(value = "用户剩余经验")
    private Integer experience;

    @ApiModelProperty(value = "连续签到天数")
    private Integer signNum;

    @ApiModelProperty(value = "用户等级")
    private Integer level;

    @ApiModelProperty(value = "关联邀请人id")
    private Long spreadUid;

    @ApiModelProperty(value = "关联邀请人时间")
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

    @ApiModelProperty(value = "微信认证数据")
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private WechatUserDto wxProfile;

    @ApiModelProperty(value = "登陆次数")
    private Integer loginCount;

    @ApiModelProperty(value = "邮件是否已验证")
    private Integer isCheked;

    @ApiModelProperty(value = "会员信息是否完善")
    private Integer infoFull;


    @ApiModelProperty(value = "小程序用户授权id")
    private String openid;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDto dto = (UserDto) o;
        return Objects.equals(id, dto.id) &&
                Objects.equals(username, dto.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

}
