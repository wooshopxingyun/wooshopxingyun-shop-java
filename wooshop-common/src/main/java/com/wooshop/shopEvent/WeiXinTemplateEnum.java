package com.wooshop.shopEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 微信模板枚举
 */

@Getter
@AllArgsConstructor
public enum WeiXinTemplateEnum {
    WEI_XIN_TEMPLATE_TYPE_1(1,"支付成功模板通知!"),
    WEI_XIN_TEMPLATE_TYPE_2(2,"退款成功通知!"),
    WEI_XIN_TEMPLATE_TYPE_3(3,"发货成功通知!"),
    WEI_XIN_TEMPLATE_TYPE_4(4,"充值成功通知!"),
    WEI_XIN_TEMPLATE_TYPE_5(5,"在线买单/收款成功通知!"),
    WEI_XIN_TEMPLATE_TYPE_6(6,"商家收款通知!"),
    WEI_XIN_TEMPLATE_TYPE_7(7,"用户下单未支付通知!"),
    WEI_XIN_TEMPLATE_TYPE_8(8,"用户提现通知!"),
    WEI_XIN_TEMPLATE_TYPE_9(9,"退款申请通知!");
    private Integer value;
    private String desc;

    public static WeiXinTemplateEnum toType(Integer value) {
        return Stream.of(WeiXinTemplateEnum.values())
                .filter(p -> p.value.equals(value))
                .findAny()
                .orElse(null);
    }

}
