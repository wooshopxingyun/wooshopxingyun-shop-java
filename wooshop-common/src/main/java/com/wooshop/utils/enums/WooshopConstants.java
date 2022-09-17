package com.wooshop.utils.enums;

/**
 * 商城统一常量
 */
public class WooshopConstants {


    /** 订单—订单取消时间(2小时分钟) 秒*/
    public static final long ORDER_OUTTIME_UNPAY = 7200;

    /** 订单—自动收货时间(7天) */
    public static final Integer ORDER_AOTO_UNCONFIRM = 7;

    /** 积分冻结时间—(7天)integral  freeze*/
    public static final Integer INTEGRAL_FREEZE_DAY = 7;

    /** 订单—订单超时未付款redis key */
    public static final String GOODS_REDIS_ORDER_OUTTIME_KEY = "goods_order:unpay:";

    /** redis订单自动收货key */
    public static final String GOODS_REDIS_ORDER_OUTTIME_UNCONFIRM = "goods_order:unconfirm:";

    /** redis拼团订单未满团自动取消key */
    public static final String GOODS_REDIS_PINK_ORDER_OUTTIME_UNCONFIRM = "goods_order:unconfirmPink:";
    /**
     * 微信支付service
     */
    public static final String WOOSHOP_WEIXIN_PAY_SERVICE = "wooshop_weixin_pay_service";
    /**
     * 微信公众号service
     */
    public static final String WOOSHOP_GONZHONGHAO_MP_SERVICE = "wooshop_gonzhonghao_mp_service";

    /**
     * 微信小程序service
     */
    public static final String WOOSHOP_WEIXIN_MP_SERVICE = "wooshop_weixin_mp_service";


    public static final String WOOSHOP_UNIAPP_WEIXIN_REMARK = "wooshop为您服务！";


    public static final String WOOSHOP_UNIAPP_WEIXIN_WELCOME = "你好，欢迎关注wooshop!！";

    //订单支付类型
//    public static final String ORDER_PAY_TYPE_GOODS="goods_order_pay";//商品支付
//    public static final String ORDER_PAY_TYPE_MONEY="money_order_pay";//充值余额支付
//    public static final String ORDER_PAY_TYPE_SVIP="svip_order_pay";//svip支付



    //时间格式
    public static final String DATE_FORMAT_DATE = "yyyy-MM-dd";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_MONTH = "yyyy-MM";
    public static final String DATE_TIME_FORMAT_NUM = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_NUM = "yyyyMMdd";
    public static final String DATE_FORMAT_START = "yyyy-MM-dd 00:00:00";
    public static final String DATE_FORMAT_END = "yyyy-MM-dd 23:59:59";
    public static final String DATE_FORMAT_MONTH_START = "yyyy-MM-01 00:00:00";
    public static final String DATE_FORMAT_YEAR_START = "yyyy-01-01 00:00:00";
    public static final String DATE_FORMAT_YEAR_END = "yyyy-12-31 23:59:59";


    //积分
    public static final String INTEGRAL_RECORD_TITLE = "后台操作积分"; //积分
    public static final String INTEGRAL_RECORD_REMARKS_ADD = "后台操作增加了{}积分"; //增加积分
    public static final String INTEGRAL_RECORD_REMARKS_SUB = "后台操作减少了{}积分"; //减少积分

    //余额
    public static final String YUE_RECORD_TITLE = "后台操作余额"; //余额
    public static final String YUE_RECORD_REMARKS_ADD = "后台操作增加了{}余额"; //增加余额
    public static final String YUE_RECORD_REMARKS_SUB = "后台操作减少了{}余额"; //减少余额

    //充值余额
    public static final String PAY_YUE_RECORD_TITLE = "用户操作充值余额"; //余额
    public static final String PAY_YUE_RECORD_REMARKS_ADD = "用户充值了{}元,赠送{}元"; //增加余额

    //签到
    public static final Integer SIGN_TYPE_INTEGRAL = 1; //积分
    public static final Integer SIGN_TYPE_EXPERIENCE = 2; //经验
    public static final String SIGN_TYPE_INTEGRAL_TITLE = "签到积分奖励"; //积分
    public static final String SIGN_TYPE_EXPERIENCE_TITLE = "签到经验奖励"; //经验

    //用户等级升级
    public static final String USER_LEVEL_OPERATE_LOG_MARK = "尊敬的用户 【{$userName}】, 在{$date}赠送会员等级成为{$levelName}会员";
    public static final String USER_LEVEL_UP_LOG_MARK = "尊敬的用户 【{$userName}】, 在{$date}您升级为为{$levelName}会员";
    public static final String USER_LEVEL_UP_LOG_MARK_SYS = "尊敬的用户 【{$userName}】, 在{$date}您通过手动升级为为{$levelName}会员";

    //经验记录
    public static final Integer EXPERIENCE_RECORD_TYPE_ADD = 1;/** 经验记录类型—增加 */
    public static final Integer EXPERIENCE_RECORD_TYPE_SUB = 2;/** 经验记录类型—扣减 */
    public static final Integer EXPERIENCE_RECORD_STATUS_CREATE = 1;/** 经验记录状态—创建 */
    public static final String EXPERIENCE_RECORD_LINK_TYPE_ORDER = "order";/** 经验记录关联类型—订单 */
    public static final String EXPERIENCE_RECORD_LINK_TYPE_SIGN = "sign";/** 经验记录关联类型—签到 */
    public static final String EXPERIENCE_RECORD_LINK_TYPE_SYSTEM = "system";/** 经验记录关联类型—系统后台 */
    public static final String EXPERIENCE_RECORD_TITLE_ORDER = "用户订单付款成功";/** 经验记录标题—用户订单付款成功 */
    public static final String EXPERIENCE_RECORD_TITLE_SIGN = "签到经验奖励"; /** 经验记录标题—签到经验奖励 */
    public static final String EXPERIENCE_RECORD_TITLE_REFUND = "用户退款";/** 经验记录标题—用户退款 */
    public static final String EXPERIENCE_RECORD_TITLE_ADMIN = "管理员操作";/** 经验记录标题—PC管理员操作 */

    //会员搜索日期类型
    public static final String SEARCH_DATE_DAY = "today"; //今天
    public static final String SEARCH_DATE_YESTERDAY = "yesterday"; //昨天
    public static final String SEARCH_DATE_LATELY_7 = "lately7"; //最近7天
    public static final String SEARCH_DATE_LATELY_30 = "lately30"; //最近30天
    public static final String SEARCH_DATE_WEEK = "week"; //本周
    public static final String SEARCH_DATE_PRE_WEEK = "preWeek"; //上周
    public static final String SEARCH_DATE_MONTH = "month"; //本月
    public static final String SEARCH_DATE_PRE_MONTH = "preMonth"; //上月
    public static final String SEARCH_DATE_YEAR = "year"; //年
    public static final String SEARCH_DATE_PRE_YEAR = "preYear"; //上一年

    //用户佣金记录 UserBrokerageRecord LinkType 关联类型（order,extract，yue）

    public static final String USER_BROKERAGE_RECORD_LINK_TYPE_ORDER = "order"; //order
    public static final String USER_BROKERAGE_RECORD_LINK_TYPE_EXTRACT = "extract"; //extract
    public static final String USER_BROKERAGE_RECORD_LINK_TYPE_YUE = "yue"; //yue
    public static final String USER_BROKERAGE_RECORD_TITLE_1 = "佣金提现"; //
    public static final String USER_BROKERAGE_RECORD_TITLE_2 = "获得用户推广订单佣金"; //

    //分销 frozenTime
    public static final Integer BROKERAGE_FROZEN_TIME = 7; //佣金冻结时间

    public static final String CONFIG_KEY_STORE_BROKERAGE_LEVEL = "store_brokerage_rate_num"; //返佣比例前缀
    public static final String CONFIG_KEY_STORE_BROKERAGE_RATE_ONE = "store_brokerage_ratio"; //一级返佣比例前缀
    public static final String CONFIG_KEY_STORE_BROKERAGE_RATE_TWO = "store_brokerage_two"; //二级返佣比例前缀
    public static final String CONFIG_KEY_STORE_BROKERAGE_USER_EXTRACT_MIN_PRICE = "user_extract_min_price"; //提现最低金额
    public static final String CONFIG_KEY_STORE_BROKERAGE_MODEL = "store_brokerage_status"; //分销模式1-指定分销2-人人分销
    public static final String CONFIG_KEY_STORE_BROKERAGE_USER_EXTRACT_BANK = "user_extract_bank"; //提现银行卡
    public static final String CONFIG_KEY_STORE_BROKERAGE_EXTRACT_TIME = "extract_time"; //佣金冻结时间
    public static final String CONFIG_KEY_STORE_INTEGRAL_EXTRACT_TIME = "freeze_integral_day"; //积分冻结时间
    public static final String CONFIG_KEY_STORE_BROKERAGE_PERSON_PRICE = "store_brokerage_price"; //人人分销满足金额
    public static final String CONFIG_KEY_STORE_BROKERAGE_IS_OPEN = "brokerage_func_status"; //分销启用
    public static final String CONFIG_KEY_STORE_BROKERAGE_BIND_TYPE = "brokerageBindind"; //分销关系绑定0-所有游湖，2-新用户



    /** 小程序登录凭证校验的url */
    public static final String WECHAT_MINI_SNS_AUTH_CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid={}&secret={}&js_code={}&grant_type=authorization_code";

    /** 公众号表常量 */
    public static final String WECHAT_MENUS =  "wechat_menus";

}
