package com.wooshop.dto;

import lombok.*;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WechatUserDto {

    private String openid;

    private String unionId;

    private String routineOpenid;

    private String nickname;

    private String headimgurl;

    private Integer sex;

    private String city;

    private String language;

    private String province;

    private String country;

    private Boolean subscribe;

    private Long subscribeTime;

    private String sessionKey;

}
