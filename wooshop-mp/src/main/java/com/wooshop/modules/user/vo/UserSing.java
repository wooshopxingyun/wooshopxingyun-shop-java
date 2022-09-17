package com.wooshop.modules.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 手机注册
 */
@Data
public class UserSing implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    @ApiModelProperty(name = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(name = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;


    @ApiModelProperty(name = "用户编码(微信授权绑定手机号码)")
//    @NotBlank(message = "用户编码")
    private Long userCode;

    /** 客户端类型 **/
    private String clientType;

    private String uuid;
}
