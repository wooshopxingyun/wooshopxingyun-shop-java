package com.wooshop.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 商城记录枚举
 */
@Getter
@AllArgsConstructor
public enum WooShopRecordEnum {

    INTEGRAL_DEDUKCTION("integral_deduction","积分抵扣"),
    INTEGRAL_SIGN("integral_sign","签到奖励"),
    INTEGRAL_GIFT("integral_gift","购买商品获得积分");
//    INTEGRAL_GIFT("integral_gift","购买商品获得积分"),

    private String key;
    private String value;



    public static WooShopRecordEnum toType(String key) {
        return Stream.of(WooShopRecordEnum.values())
                .filter(p -> p.key.equals(key))
                .findAny()
                .orElse(null);
    }

    public static String getDesc(String key) {
        return toType(key) == null ? null : toType(key).value;
    }
}
