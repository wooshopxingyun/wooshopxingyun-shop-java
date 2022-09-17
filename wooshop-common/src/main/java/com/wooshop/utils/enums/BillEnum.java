package com.wooshop.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;


@Getter
@AllArgsConstructor
public enum BillEnum {

    BILL_TYPE_1("recharge","充值"),
    BILL_TYPE_2("consume","消费"),
    BILL_TYPE_3("brokerage","返佣"),
    BILL_TYPE_4("rebate","退款"),
    BILL_TYPE_5("withdraw","提现"),
    BILL_TYPE_6("sign","签到"),
    BILL_TYPE_7("sys_add","系统添加"),
    BILL_TYPE_8("sys_sub","系统减少"),
    BILL_TYPE_9("deduction","减去"),
    BILL_TYPE_10("gain","奖励"),

    BILL_CATEGORY_MONEY("yuepay","金额"),
    BILL_CATEGORY_INTEGRAL("integral","积分");
//    ALI_PAY("alipay","支付宝支付"),
//    WEIXIN_PAY("weixinpay","微信支付"),
//    YUE_PAY("yuepay","余额支付"),
//    INTEGRAL_PAY("integralpay","积分兑换");
    private String key;
    private String value;



    public static BillEnum toType(String key) {
        return Stream.of(BillEnum.values())
                .filter(p -> p.key.equals(key))
                .findAny()
                .orElse(null);
    }

    public static String getDesc(String key) {
        return toType(key) == null ? null : toType(key).value;
    }
}
