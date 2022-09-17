package com.wooshop.config;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.wooshop.constant.SysConfigEnum;
import com.wooshop.constant.WooshopSysConfigConstant;
import com.wooshop.modules.sys_config.domain.WooSysConfig;
import com.wooshop.modules.sys_config.service.WooSysConfigService;
import com.wooshop.security.cache.Cache;
import com.wooshop.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 启动加载到redis 缓存
 */
@Slf4j
@Configuration
public class RedisSysConfig {

    @Autowired
    private Cache cache;

    @Autowired
    private WooSysConfigService wooSysConfigService;

    @Bean
    public void testredis() {
        List<WooSysConfig> list = wooSysConfigService.list();
        cache.likeDelKey(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_Like);//启动前清理缓存

        for (WooSysConfig woosysconfig : list) {
            System.out.println(woosysconfig.getMenuName());
            System.out.println();
            //短信配置
            if (StrUtil.equals(woosysconfig.getMenuName(), SysConfigEnum.WOOSHOP_SYSCONFIG_ALIYUNSMS.toString())) {
                JSONObject json = new JSONObject(woosysconfig.getValue());//获取json 数据
                //判断 enabled 是否开启  把开启状态存到redis中
                if (woosysconfig.getEnabled()==1) {
                    //把值存到redis
                    cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_ALIYUNSMS_ACCESSKEY, json.getStr("accessKey"));
                    cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_ALIYUNSMS_ACCESSKEYSECRET, json.getStr("accessKeySecret"));
                    cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_ALIYUNSMS_SIGNNAME, json.getStr("signName"));
                    cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_ALIYUNSMS_TEMPLATEID, json.getStr("templateId"));
                    cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_ALIYUNSMS_REGION, json.getStr("region"));
                }
                cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_ALIYUNSMS_ENABLED, woosysconfig.getEnabled());

            }
            //判断微信小程序配置
            if (StrUtil.equals(woosysconfig.getMenuName(), SysConfigEnum.WOOSHOP_SYSCONFIG_WECHATMP.toString())) {
                JSONObject json = new JSONObject(woosysconfig.getValue());//获取json 数据
                //把值存到redis
                cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_WECHATMP_APPID, json.getStr("appId"));
                cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_WECHATMP_APPSECRET, json.getStr("appSecret"));
            }
            //分销配置
            if (StrUtil.equals(woosysconfig.getMenuName(), SysConfigEnum.WOOSHOP_SYSCONFIG_DISTRIBUTION.toString())) {
                JSONObject json = new JSONObject(woosysconfig.getValue());//获取json 数据
                //把值存到redis
                if (woosysconfig.getEnabled()==1) {
                    cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_DISTRIBUTION_TWO, json.getStr("distribution"));
                    cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_DISTRIBUTION_ONE, json.getStr("distributionTwo"));
                    cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_DISTRIBUTION_EXTRACTMONEY, json.getStr("extractMoney"));
                }
                cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_DISTRIBUTION_ENABLED, woosysconfig.getEnabled());//状态
            }
            //积分配置
            if (StrUtil.equals(woosysconfig.getMenuName(), SysConfigEnum.WOOSHOP_SYSCONFIG_INTEGRAL.toString())) {
                JSONObject json = new JSONObject(woosysconfig.getValue());//获取json 数据
                //把值存到redis
                cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_INTEGRAL_CONVERTIBILITY, json.getStr("convertibility"));//积分抵用比例
                cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_INTEGRAL_GIVEINTEGRAL, json.getStr("giveIntegral"));//赠送积分比例
            }
            //签到天数配置 奖励积分
            if (StrUtil.equals(woosysconfig.getMenuName(), SysConfigEnum.WOOSHOP_SYSCONFIG_SIGNCONFIG.toString())) {
                JSONObject json = new JSONObject(woosysconfig.getValue());//获取json 数据
                JSONArray value = json.getJSONArray("value");
                for (int i = 0; i < value.size(); i++) {
                    JSONObject jsonObject = value.getJSONObject(i);
                    if (jsonObject.getStr("signDay").equals("dayOne")){
                        cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_SIGDAY_ONE, json.getStr("giveIntegral"));//签到第一天数奖励积分
                    }else if (jsonObject.getStr("signDay").equals("dayTwo")){
                        cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_SIGDAY_TWO, json.getStr("giveIntegral"));//签到第二天数奖励积分
                    }else if (jsonObject.getStr("signDay").equals("dayThree")){
                        cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_SIGDAY_THREE, json.getStr("giveIntegral"));//签到第三天数奖励积分
                    }else if (jsonObject.getStr("signDay").equals("dayFour")){
                        cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_SIGDAY_FOUR, json.getStr("giveIntegral"));//签到第四天数奖励积分
                    }else if (jsonObject.getStr("signDay").equals("dayFive")){
                        cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_SIGDAY_FIVE, json.getStr("giveIntegral"));//签到第五天数奖励积分
                    }else if (jsonObject.getStr("signDay").equals("daySix")){
                        cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_SIGDAY_SIX, json.getStr("giveIntegral"));//签到第六天数奖励积分
                    }else if (jsonObject.getStr("signDay").equals("daySeven")){
                        cache.put(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_SIGDAY_SEVEN, json.getStr("giveIntegral"));//签到第七天数奖励积分
                    }

                }

            }

            //判断公众号配置
            //判断微信支付配置


        }

        System.out.println("测试启动是否加载配置" + list.get(0));
        System.out.println("测试启动是否加载配置");
    }
}
