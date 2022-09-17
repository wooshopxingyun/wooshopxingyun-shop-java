package com.wooshop.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 支付方法枚举
 */
@Getter
@AllArgsConstructor
public enum PayWayEnum {


    WECHATH5("MP_WeiXinH5","公众号支付"),
    WXAPP("MP_WeiXin","小程序支付"),
    APP("app","app支付"),
    PC("PC","pc支付");



    private String value;
    private String desc;

    public static PayWayEnum toType(String value) {
        return Stream.of(PayWayEnum.values())
                .filter(p -> p.value.equals(value))
                .findAny()
                .orElse(null);
    }
}
