package com.wooshop.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 *  支付类型
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum {


    ALI_PAY("alipay","支付宝支付"),
    WEIXIN_PAY("weixinpay","微信支付"),
    YUE_PAY("yuepay","余额支付"),
    INTEGRAL_PAY("integralpay","积分兑换");

    private String value;
    private String desc;

    public static PayTypeEnum toType(String value) {
        return Stream.of(PayTypeEnum.values())
                .filter(p -> p.value.equals(value))
                .findAny()
                .orElse(null);
    }
}
