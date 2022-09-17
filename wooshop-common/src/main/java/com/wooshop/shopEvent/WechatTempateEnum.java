package com.wooshop.shopEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum WechatTempateEnum {
    PAY_SUCCESS("pay_success","支付成功通知"),
    DELIVERY_SUCCESS("delivery_success","发货成功通知"),
    REFUND_SUCCESS("refund_success","退款成功通知"),
    REMOVE_SUCCESS("remove_success","删除成功"),
    RECHARGE_SUCCESS("recharge_success","充值成功通知"),
    WEIXIN_TEMP_TYPE("temp","模板消息"),
    WEIXIN_SUB_TYPE("sub","订阅消息");

    private String value; //模板编号
    private String desc; //模板id

}
