package com.wooshop.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 商城订单枚举
 */
@Getter
@AllArgsConstructor
public enum OrderEnum {

    CREATE_ORDER_SUCCESS("SUCCESS","订单创建成功"),
    EXTEND_ORDER_SUCCESS("EXTEND_ORDER_SUCCESS","订单已生成"),
    EXTEND_ORDER_ERROR("EXTEND_ORDER_ERROR","订单生成失败"),
    PAY_YUE_ORDER_SUCCESS("PAY_YUE_ORDER_SUCCESS","余额支付成功"),
    PAY_YUE_ORDER_ERROR("PAY_YUE_ORDER_ERROR","余额支付不足"),
    PAY_ALI_ORDER_ERROR("PAY_ALI_ORDER_ERROR","支付宝支付失败"),
    PAY_ALI_ORDER_SUCCESS("PAY_ALI_ORDER_SUCCESS","支付宝支付成功"),
    PAY_WEIXIN_ORDER_SUCCESS("PAY_WEIXIN_ORDER_SUCCESS","微信支付成功"),
    PAY_WEIXIN_ORDER_ERROR("PAY_WEIXIN_ORDER_ERROR","微信支付失败"),
    PAY_USER_ORDER_SUCCESS("PAY_USER_ORDER_SUCCESS","用户付款成功"),

    DELIVERY_ORDER_SUCCESS("DELIVERY_ORDER_SUCCESS", "商家已发货"),
    DELIVERY_USER_ORDER_SUCCESS("DELIVERY_USER_ORDER_SUCCESS", "已核销"),
    USER_ORDER_SUCCESS("USER_ORDER_SUCCESS", "买家已收货"),
    AUTO_USER_ORDER_SUCCESS("AUTO_USER_ORDER_SUCCESS", "系统自动确认收货"),
    EVAL_USER_ORDER_SUCCESS("EVAL_USER_ORDER_SUCCESS", "用户评价!规格:{}"),
    DEIT_PRICE_ORDER_SUCCESS("DEIT_PRICE_ORDER_SUCCESS", "修改订单价格"),
    USER_REFUND_ORDER("REFUND_ORDER_SUCCESS", "用户申请退货退款"),
    ADMIN_REFUND_PRICE_ORDER("ADMIN_REFUND_PRICE_ORDER", "管理员同意退款申请"),
    ADMIN_REFUND_GOODS_ORDER("ADMIN_REFUND_GOODS_ORDER", "管理员同意退货申请"),
    ADMIN_REFUND_CANCEL_ORDER("ADMIN_REFUND_CANCEL_ORDER", "拒绝退款申请,理由:{}"),
    CANCEL_REFUND_SUCCESS("CANCEL_REFUND_SUCCESS", "用户撤销退款退货申请"),
    REFUND_ORDER_SUCCESS("REFUND_ORDER_SUCCESS", "用户申请退货退款成功"),
    REMOVE_ORDER_SUCCESS("REMOVE_ORDER_SUCCESS", "用户删除订单"),
    AUTO_CANCEL_ORDER_ERROR("AUTO_CANCEL_ORDER_ERROR", "用户未付款自动取消订单!"),
    USER_CANCEL_ORDER_ERROR("USER_CANCEL_ORDER_ERROR", "用户操作取消未付款订单!"),
    SYS_AUTO_CANCEL_USER_PINK_ORDER("SYS_AUTO_CANCEL_USER_PINK_ORDER", "订单拼团人数未满员,系统自动取消拼团!"),
    SYS_CANCEL_PINK_ORDER_SUCCESS("SYS_CANCEL_PINK_ORDER_SUCCESS", "未成团系统自动取消订单退款成功!"),

    EDIT_REMARK_ORDER_START("EDIT_REMARK_ORDER_START", "管理员添加备注:{}"),
    EDIT_ADDRESS_ORDER_iNFO("EDIT_ADDRESS_ORDER_iNFO", "管理员修改收货地址:原收货信息{}"),
    EDIT_PRICE_ORDER_iNFO("EDIT_PRICE_ORDER_iNFO", "管理员修改订单价格:原支付金额{},修改后支付金额{}"),
    EDIT_VERIFY_CODE_ORDER_INFO("EDIT_VERIFY_CODE_ORDER_iNFO", "管理员核销订单成功!"),
    EDIT_DELIVERY_ORDER_INFO("EDIT_DELIVERY_ORDER_INFO", "商家已发货,{},{},{}");



    private String key;
    private String value;



    public static OrderEnum toType(String key) {
        return Stream.of(OrderEnum.values())
                .filter(p -> p.key.equals(key))
                .findAny()
                .orElse(null);
    }

    public static String getDesc(String key) {
        return toType(key) == null ? null : toType(key).value;
    }
}
