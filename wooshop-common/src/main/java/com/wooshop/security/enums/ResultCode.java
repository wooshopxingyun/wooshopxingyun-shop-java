package com.wooshop.security.enums;

/**
 * 返回状态码
 * 第一位 1:商品；2:用户；3:交易,4:促销,5:店铺,6:页面,7:设置,8:其他
 *
 * @author woo
 * @version v4.0
 * @since 20202/06/14 16:20
 */
public enum ResultCode {

    /**
     * 成功状态码
     */
    SUCCESS(200, "成功"),

    /**
     * 失败返回码
     */
    ERROR(400, "服务器繁忙，请稍后重试"),

    /**
     * 失败返回码
     */
    DEMO_SITE_EXCEPTION(4001, "演示站点禁止使用"),
    /**
     * 参数异常
     */
    PARAMS_ERROR(4002, "参数异常"),


    /**
     * 系统异常
     */
    VERIFICATION_EXIST(0001, "验证码服务未开启"),
    WECHAT_SYSCONFIG_NOT_EXIST(0002,"小程序登录未配置"),
    WECHAT_MP_AUTHORIZTION_MESSAGE_ERROR(0003, "未能获取到微信模版消息id"),
    SYS_IMAGE_UPLOAD_MESSAGE_ERROR(0004, "只能上传图片"),
    APP_H5_URL_SYSCONFIG_NOT_EXIST(0005, "未配置h5地址"),
    APP_API_URL_SYSCONFIG_NOT_EXIST(0006, "未配置API地址"),
    POSTER_IMG_EXIST(0007, "砍价海报生成失败"),
    WECHAT_API_ERROR_RES_NOT(0010, "微信平台接口异常，没任何数据返回！"),
    WECHAT_API_ERROR(0011, "微信接口调用失败！"),

    /**
     * 用户 异常
     */

    USER_NOT_EXIST(1001, "用户不存在"),//正在使用
    USER_NOT_LOGIN(1002, "用户未登录"),
    USER_AUTH_EXPIRED(1004, "当前登录已经失效，请重新登录"),
    USER_PASSWORD_ERROR(1005, "密码不正确"),
    USER_AUTHORITY_ERROR(1006, "权限不足"),
    USER_SIGN_SUCCESS(1006,"注册成功"),
    USER_PHONE_EXIST(1007, "该手机号已被注册"),
    USER_PHONEORCODE_ISNOT(1008,"手机号码或者验证码不能是空"),
    USER_PHONE_NOT_SIGN(1009, "手机号未注册"),
    WITHDRAW_MONEY_NOT_ERROR(1010, "申请提现佣金不符合"),
    WITHDRAW_MONEY_CANCEL_NOT_ERROR(1011, "取消提现数据不存在!"),



    USER_CONNECT_LOGIN_ERROR(20006, "未找到登录信息"),
    USER_EDIT_SUCCESS(20001, "用户修改成功"),
    USER_NAME_EXIST(20007, "该用户名已被注册"),

    /**
     * 签到
     */
    SIGN_DATA_NOT_EXIST(20008, "今日已签到。不可重复签到!"),
    SIGN_SYS_NOT_EXIST(20009, "签到配置不存在，请在管理端配置签到数据"),
    SIGN_CONFIG_SYS_PARAMS(20010, "请先配置签到天数!"),
    SIGN_USER_ERROR(20011, "修改用户签到信息失败!"),


    /**
     * 验证码
     */
    VERIFICATION_SEND_SUCCESS(2001, "短信验证码,发送成功请留意！"),//在使用
    VERIFICATION_SMS_EXPIRED_ERROR(2002, "验证码已失效，请重新校验"),


    /**
     * 商品
     */
    WOOSHOP_GOODS_NOT_EXIST_ERROR(3000,"商品已下架!"),

    /**
     * 优惠券
     */
    WOOSHOP_COUPONS_NOT_EXIST_ERROR(4000,"优惠券不存在!"),
    WOOSHOP_COUPON_EXPIRED(4001, "优惠券已过期/已被领取完，不能领取!"),
    WOOSHOP_COUPON_LIMIT_ERROR(4002, "超出领取限制"),
    WOOSHOP_GET_COUPON_INFO_SUCCESS(4003, "优惠券领取成功!"),
    WOOSHOP_COUPONS_PRICE_NOT_EXIST_ERROR(4004,"订单金额不满足不能使用满减优惠券"),

    /**
     * 购物车
     */
    WOOSHOP_CART_SUCCESS(5000, "更新购物车成功!"),
    WOOSHOP_CART_NOT_EXIST_ERROR(5001, "商品提交已失效,请重新进行结算!"),
    WOOSHOP_ADDRESS_NOT_EXIST_ERROR(5002, "收货地址为空!"),
    WOOSHOP_STORES_NOT_EXIST_ERROR(5003, "自提店铺地址为空,请联系管理员!"),
    WOOSHOP_INTEGRAL_NOT_EXIST_ERROR(5003, "兑换积分不够,请重新提交!"),
    WOOSHOP_INTEGRALPRICE_NOT_EXIST_ERROR(5005, "兑换金额不够,请重新提交!"),
    WOOSHOP_USER_PHONE_NOT_EXIST_ERROR(5006, "联系人和联系电话不能为空!"),


    /**
     * 订单
     */
    ORDER_PAY_NOT_EXIST(6000, "支付订单不存在"),
    ORDER_IS_PAY_SUCCESS(6001, "该订单已支付"),
    ORDER_PAY_IS_NOT_EXIST(6002, "该支付无需支付"),
    RECHARGE_ORDER_IS_NOT_EXIST(6003, "充值订单不存在!"),
    RECHARGE_ORDER_IS_EXIST(6004, "充值订单已支付!"),
    ORDER_YUE_PAY_IS_NOT_EXIST(6005, "余额不足,请选择其它支付方式!"),
    ORDER_CANCEL_NOT_EXIST(6006, "取消订单失败,订单不存在"),
    GOODS_SUK_ORDER_CANCEL_NOT_EXIST(6007, "订单商品suk不存在"),
    GOODS_SUK_QUANTITY_NOT_EXIST(6008, "商品库存不足!"),
    ORDER_IS_NOT_EXIST(6009, "商品订单信息不存在"),
    ORDER_NOT_PAY_BACK_EXIST(6010, "订单未付款 不支持退款/退货"),
    ORDER_START_SUCCESS_NOT_BACK_EXIST(6011, "订单已完成,不支持退款/退货"),
    ORDER_CHECK_BACK_EXIST(6012, "订单申请退款/退货,正在审核中"),
    ORDER_CHECK_BACK_SUCCESS(6013, "订单已成功退款,请勿重复提交"),
    ORDER_SUCCESS_PAY_CANCEL_EXIST(6014, "订单已付不支持取消订单"),
    ORDER_REFUND_IS_NOT_EXIST(6015, "订单未申请退款,不支持撤销"),
    ORDER_REFUND_START_SUCCESS(6016, "订单已退款,不支持撤销"),
    CONFIG_ORDER_NOT_EXIST(6017, "订单已确认收货!"),
    ORDER_CHECK_ING_BACK_EXIST(6017, "订单已退款,不支持确认收货"),
    ORDER_EVALUATION_IS_NOT_EXIST(6018, "评价商品信息不存在"),
    ORDER_REFUND_IS_DEL_ORDER(6019, "订单已申请退款中,不支持删除"),
    ORDER_START_IS_NOT(6020, "订单未确认收货,不支持删除"),
    /**
     * 移动端首页
     */
    HOME_SECKILL_NOT_OPEN(7000, "秒杀活动页未开启!"),
    HOME_BARGAIN_NOT_OPEN(7001, "砍价活动页未开启!"),
    HOME_GROUPBUYING_NOT_OPEN(7002, "拼团活动页未开启!"),
    HOME_TOPGOODS_NOT_OPEN(7003, "普通商品排行榜未开启!"),



    /**
     * 砍价商品
     */
    BARGAIN_NOT_EXIST(8000, "未找到对应砍价商品信息!"),
    BARGAIN_IS_START_NOT_EXIST(8001, "砍价商品已下架!"),
    BARGAIN_TIME_OUT_NOT_EXIST(8002, "商品活动已过期,去查看其它砍价商品吧!"),
    BARGAIN_QUANTITY_NOT_EXIST(8003, "砍价商品已售罄!"),
    BARGAIN_USER_NOT_FINISHED(8004, "请先完成当前砍价活动!"),
    BARGAIN_USER_IS_ASTRICT(8005, "您已达到当前砍价活动上限!"),
    BARGAIN_LAUNCH_USER_NOT_EXIST(8006, "发起砍价用户编号不能为空!"),
    BARGAIN_LAUNCH_USER_INFO_NOT_EXIST(8007, "砍价商品用户信息不存在!"),
    BARGAIN_PRICE_SUCCESS(8008, "商品已完成砍价!"),
    BARGAIN_NUM_SUCCESS(8009, "商品砍价次数已经完成!"),
    BARGAIN_HELP_NUM_SUCCESS(8010, "您参与砍价次数已经用完!"),
    BARGAIN_USER_NOT_FINISHED_ORDER_SUCCESS(8011, "订单已提交过,请先去支付完成订单!"),

    /**
     * 团购商品
     */
    GROUPBOOKING_NOT_EXIST(9000, "未找到对应拼团商品信息!"),
    GROUPBOOKING_IS_START_NOT_EXIST(9001, "拼团商品已下架!"),
    GROUPBOOKING_TIME_OUT_NOT_EXIST(9002, "商品活动已过期,去查看其它拼团商品!"),
    GROUPBOOKING_USER_NOT_EXIST(9003, "未找到对应拼团用户信息!"),
    GROUPBOOKING_GOODS_SUK_NOT_EXIST(9004, "未找到对应拼团商品规格信息!"),
    GROUPBOOKING_GOODS_SUK_QUANTITY_NOT_EXIST(9005, "拼团商品库存不足!"),
    GROUPBOOKING_GOODS_SUK_RESTRICTION_NOT_EXIST(9006, "下单数量超过限购数量!"),
    GROUPBOOKING_USER_SATISFY_SUCCESS(9007, "拼团已经满员,无法参与该团!"),
    GROUPBOOKING_USER_RESTRICTION_SUCCESS(9008, "您已参与过该团,请勿重复参与!"),
    GROUPBOOKING_USER_is_EXIST(9009, "加入购物车失败!"),
    GROUPBOOKING_USER_TIME_OUT_NOT_EXIST(9010, "提交的拼团信息已经失效,请重新提交!"),

    /**
     * 秒杀商品
     */
    SECKILL_SYS_IS_NOT_EXIST(1100, "当前秒杀时段没有配置!"),
    SECKILL_GOODS_IS_NOT_EXIST(1101, "未找到对应秒杀商品信息!"),
    SECKILL_GOODS_ORDER_SUM_IS_NOT_EXIST(1102, "今天参与该商品次数已达上限,可以参与其他秒杀商品!"),
    SECKILL_GOODS_SUK_IS_NOT_EXIST(1103, "未找到商品规格信息!"),
    SECKILL_GOODS_ACTIVITY_OUT_DATE(1104, "商品活动已过期,去查看其它秒杀商品!"),
    SECKILL_GOODS_SUK_QUANTITY_NOT_EXIST(1104, "秒杀商品库存不足!"),
    SECKILL_GOODS_TIME_IS_NOT_EXIST(1106, "当前秒杀时段,未找到对应秒杀商品信息!"),
    SECKILL_GOODS_CART_SUM_IS_NOT_EXIST(1105, "购买数量超出限制!"),


    /**
     * 余额充值
     */

    PAY_MONEY_IS_NOT_NULL(1200, "充值金额不能为空!");

    private final Integer code;
    private final String message;


    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

}
