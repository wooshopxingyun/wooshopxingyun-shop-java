package com.wooshop.constant;

/**
 * 系统常量配置
 */
public class WooshopSysConfigConstant {

    public final static String WOOSHOP_SYSCONFIG_Like ="WOOSHOP_SYSCONFIG_*";//以WOOSHOP_SYSCONFIG_为开头

   /* -----------------------------------------  阿里云短信   ----------------------------------------------  */
    public final static String WOOSHOP_SYSCONFIG_SYSCONFIG_ALIYUNSMS="WOOSHOP_SYSCONFIG_SYSCONFIG_ALIYUNSMS";//数据menu：name
    public final static String WOOSHOP_SYSCONFIG_ALIYUNSMS_SIGNNAME="WOOSHOP_SYSCONFIG_ALIYUNSMS_SIGNNAME";
    public final static String WOOSHOP_SYSCONFIG_ALIYUNSMS_TEMPLATEID="WOOSHOP_SYSCONFIG_ALIYUNSMS_TEMPLATEID";
    public final static String WOOSHOP_SYSCONFIG_ALIYUNSMS_ACCESSKEY="WOOSHOP_SYSCONFIG_ALIYUNSMS_ACCESSKEY";
    public final static String WOOSHOP_SYSCONFIG_ALIYUNSMS_REGION="WOOSHOP_SYSCONFIG_ALIYUNSMS_REGION";
    public final static String WOOSHOP_SYSCONFIG_ALIYUNSMS_ACCESSKEYSECRET="WOOSHOP_SYSCONFIG_ALIYUNSMS_ACCESSKEYSECRET";//
    public final static String WOOSHOP_SYSCONFIG_ALIYUNSMS_ENABLED="WOOSHOP_SYSCONFIG_ALIYUNSMS_ENABLED";//状态
    public final static String WOOSHOP_SYSCONFIG_ALIYUNSMS_LIKe ="WOOSHOP_SYSCONFIG_ALIYUNSMS_*";//状态
    public final static int    WOOSHOP_SYSCONFIG_SCODESIZE=6;//6位数字
    public final static Long   WOOSHOP_SYSCONFIG_TIME=600l;//短信缓存时间
    public final static Object WOOSHOP_ALIYUNSMS="WOOSHOP_ALIYUNSMS";//缓存短信前缀

 /* ---------------------------------------------- 微信小程序配置 -------------------------------------------*/
    public final static String WOOSHOP_SYSCONFIG_WECHATMP="WOOSHOP_SYSCONFIG_WECHATMP";//微信配置 名称常量
    public final static String WOOSHOP_SYSCONFIG_WECHATMP_APPID="WOOSHOP_SYSCONFIG_WECHATMP_APPID";//APPID
    public final static String WOOSHOP_SYSCONFIG_WECHATMP_APPSECRET="WOOSHOP_SYSCONFIG_WECHATMP_APPSECRET";//appSecret

 /* ---------------------------------------------- 分销配置 -------------------------------------------*/
    public final static String WOOSHOP_SYSCONFIG_DISTRIBUTION_ONE="WOOSHOP_SYSCONFIG_DISTRIBUTION_ONE";//一级分销配置 名称常量
    public final static String WOOSHOP_SYSCONFIG_DISTRIBUTION_TWO="WOOSHOP_SYSCONFIG_DISTRIBUTION_TWO";//二级分销配置
    public final static String WOOSHOP_SYSCONFIG_DISTRIBUTION_EXTRACTMONEY="WOOSHOP_SYSCONFIG_DISTRIBUTION_EXTRACTMONEY";//最低提现金额
    public final static String WOOSHOP_SYSCONFIG_DISTRIBUTION_ENABLED="WOOSHOP_SYSCONFIG_DISTRIBUTION_ENABLED";//分销状态

 /* ---------------------------------------------- 积分配置 -------------------------------------------*/
    public final static String WOOSHOP_SYSCONFIG_INTEGRAL_CONVERTIBILITY="WOOSHOP_SYSCONFIG_INTEGRAL_CONVERTIBILITY";//积分抵用比例
    public final static String WOOSHOP_SYSCONFIG_INTEGRAL_GIVEINTEGRAL="WOOSHOP_SYSCONFIG_INTEGRAL_GIVEINTEGRAL";//赠送积分比例

 /* ---------------------------------------------- 签到天数配置 奖励积分-------------------------------------------*/
    public final static String WOOSHOP_SYSCONFIG_SIGDAY_ONE="WOOSHOP_SYSCONFIG_SIGDAY_ONE";//第一天奖励积分
    public final static String WOOSHOP_SYSCONFIG_SIGDAY_TWO="WOOSHOP_SYSCONFIG_SIGDAY_TWO";//第二天奖励积分
    public final static String WOOSHOP_SYSCONFIG_SIGDAY_THREE="WOOSHOP_SYSCONFIG_SIGDAY_THREE";//第三天奖励积分
    public final static String WOOSHOP_SYSCONFIG_SIGDAY_FOUR="WOOSHOP_SYSCONFIG_SIGDAY_FOUR";//第四天奖励积分
    public final static String WOOSHOP_SYSCONFIG_SIGDAY_FIVE="WOOSHOP_SYSCONFIG_SIGDAY_FIVE";//第五天奖励积分
    public final static String WOOSHOP_SYSCONFIG_SIGDAY_SIX="WOOSHOP_SYSCONFIG_SIGDAY_SIX";//第六天奖励积分
    public final static String WOOSHOP_SYSCONFIG_SIGDAY_SEVEN="WOOSHOP_SYSCONFIG_SIGDAY_SEVEN";//第七天奖励积分

    //播放状态变化事件，detail = {code}
    public static final String BINDSTATECHANGE = "bindstatechange";


}
