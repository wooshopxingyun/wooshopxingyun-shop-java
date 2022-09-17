package com.wooshop.utils.enums;

/**
 * 用户账单记录常量
 */
public class UserBillConstants {

    /** 佣金记录类型—增加 */
    public static final Integer INTEGRAL_RECORD_TYPE_ADD = 1;

    /** 佣金记录类型—扣减 */
    public static final Integer INTEGRAL_RECORD_TYPE_SUB = 2;

    /** 佣金记录状态—创建 */
    public static final Integer INTEGRAL_RECORD_STATUS_CREATE = 1;

    /** 佣金记录状态—冻结期 */
    public static final Integer INTEGRAL_RECORD_STATUS_FROZEN = 2;

    /** 佣金记录状态—完成 */
    public static final Integer INTEGRAL_RECORD_STATUS_COMPLETE = 3;

    /** 佣金记录状态—失效（订单退款） */
    public static final Integer INTEGRAL_RECORD_STATUS_INVALIDATION = 4;

    /** 佣金记录关联类型—订单 */
    public static final String INTEGRAL_RECORD_LINK_TYPE_ORDER = "order";

    /** 佣金记录关联类型—签到 */
    public static final String INTEGRAL_RECORD_LINK_TYPE_SIGN = "sign";

    /** 佣金记录关联类型—系统后台 */
    public static final String INTEGRAL_RECORD_LINK_TYPE_SYSTEM = "system";

    /** 佣金记录标题—用户订单付款成功 */
    public static final String BROKERAGE_RECORD_TITLE_ORDER = "用户订单付款成功";

    /** 佣金记录标题—签到经验奖励 */
    public static final String BROKERAGE_RECORD_TITLE_SIGN = "签到积分奖励";

    /** 佣金记录标题—后台积分操作 */
    public static final String BROKERAGE_RECORD_TITLE_SYSTEM = "后台积分操作";

    /** 佣金记录标题—订单退款 */
    public static final String BROKERAGE_RECORD_TITLE_REFUND = "订单退款";

    /**   */

    /** 会员账单记录:积分 */
    public static final String INTEGRAL= "integral";
    /** 余额支付 */
    public static final String YUE_PAY= "yuepay";
    /** 微信支付 */
    public static final String WEIXIN_PAY= "weixinpay";
    /** 支付宝支付 */
    public static final String ALI_PAY= "alipay";
    /** 会员账单记录类型—积分抵扣 */
    public static final String INTEGRAL_TYPE_DEDUCTION = "integralpay";
    /** 用户充值余额 */
    public static final String USER_RECHARGE= "recharge";
    /** 用户充值余额-标题 */
    public static final String USER_RECHARGE_TITLE= "充值余额";

    /** 余额支付 */
    public static final String YUE_PAY_TITLE= "余额支付";
    /** 微信支付 */
    public static final String WEIXIN_PAY_TITLE= "微信支付";
    /** 支付宝支付 */
    public static final String ALI_PAY_TITLE= "支付宝支付";
    /** 积分兑换 */
    public static final String INTEGRAL_PAY_TITLE= "积分兑换";



    /** 会员账单记录:类型—购买商品 */
    public static final String YUE_PAY_TYPE_GOODS= "pay_goods";

    /** 会员账单记录:类型—订单退款 */
    public static final String PAY_TYPE_GOODS_REFUND = "pay_goods_refund";

    /** 会员账单记录:标题—购买商品 */
    public static final String YUE_PAY_TYPE_GOODS_TITLE= "购买商品";

    /** 会员账单记录:标题—订单退款 */
    public static final String PAY_TYPE_GOODS_RECORD_TITLE_REFUND = "订单退款";


    /** 会员账单记录类型—购买商品获得积分 */
    public static final String INTEGRAL_TYPE_GET_GOODS= "get_goods_integral";

    /** 会员账单记录—购买商品获得积分 */
    public static final String INTEGRAL_GIFT_ORDER = "购买商品获得积分";

    /** 会员账单记录类型—积分抵扣 */
    public static final String INTEGRAL_DEDUCTION_TITLE = "积分抵扣";

    /** 会员账单记录:金额 */
    public static final String MONEY= "money";

    /** 会员账单记录类型—提现失败 */
    public static final String MONEY_EXIST_DEDUCTION_TITLE = "提现失败";


}
