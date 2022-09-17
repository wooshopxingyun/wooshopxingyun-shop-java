package com.wooshop.modules.utils;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.wooshop.constant.SysConfigEnum;
import com.wooshop.domain.PageResult;
import com.wooshop.dozer.service.IGenerator;
import com.wooshop.modules.sys_config.service.WooSysConfigService;
import com.wooshop.modules.sys_config.service.dto.WooSysConfigDto;
import com.wooshop.modules.sys_config.service.dto.WooSysConfigQueryCriteria;
import com.wooshop.modules.sys_config.vo.SysConfigHomeVo;
import com.wooshop.utils.RedisUtils;
import com.wooshop.utils.enums.WooshopConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理缓存key值的统一入口
 */
@Service
@Slf4j
public class RedisUache {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IGenerator generator;
    @Autowired
    private WooSysConfigService wooSysConfigService;
    @Value("${spring.profiles.active}")
    private String profiles_active;

    /**
     *扩展值，默认为空， 把这个值追加到所有key值上
     */
    public String getExtendValue(){
        String extendValue= "";
        return  extendValue;
    }
/*-------------------------------------------------------------   阿里云   ------------------------------------------------------------------*/
    /**
     * 阿里云短信
     * @return
     */
    public JSONObject getAliYunNsms(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_ALIYUNSMS.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject;
    }

    /**
     * 阿里云短信
     * @return signName
     */
    public String getAliYunNsms_signName(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_ALIYUNSMS.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("signName");
    }

    /**
     * 阿里云短信
     * @return templateId
     */
    public String getAliYunNsms_templateId(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_ALIYUNSMS.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("templateId");
    }

    /**
     * 阿里云短信
     * @return accessKeySecret
     */
    public String getAliYunNsms_accessKeySecret(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_ALIYUNSMS.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("accessKeySecret");
    }

    /**
     * 阿里云短信
     * @return accessKey
     */
    public String getAliYunNsms_accessKey(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_ALIYUNSMS.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("accessKey");
    }

    /**
     * 阿里云短信
     * @return region
     */
    public String getAliYunNsms_region(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_ALIYUNSMS.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("region");
    }
/*-------------------------------------------------------------  微信小程序  ------------------------------------------------------------------*/
    /**
     * 微信小程序:
     * @return
     */
    public JSONObject getWechatMP(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_WECHATMP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject;
    }

    /**
     * 微信小程序:appSecret
     * @return appSecret
     */
    public String getWechatMP_appSecret(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_WECHATMP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("appSecret");
    }

    /**
     * 微信小程序:appId
     * @return appId
     */
    public String getWechatMP_appId(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_WECHATMP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("appId");
    }

    /**
     * 微信小程序:单独注册商户号状态 wechatMpPay
     * @return wechatMpPay
     */
    public Integer getWechatMP_wechatMpPay(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_WECHATMP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getInt("wechatMpPay");
    }

    /**
     * 微信小程序:单独注册商户号编码 mchId
     * @return mchId
     */
    public String getWechatMP_mchId(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_WECHATMP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("mchId");
    }

    /**
     * 微信小程序:单独注册商户 :秘钥
     * @return secretKey
     */
    public String getWechatMP_secretKey(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_WECHATMP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("secretKey");
    }

    /**
     * 微信小程序:单独注册商户 :证书地址
     * @return payCertificateName
     */
    public String getWechatMP_url(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_WECHATMP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("payCertificateName");
    }
/*-------------------------------------------------------------  商城配置   ------------------------------------------------------------------*/
    /**
     * 商城配置
     * @return DISTRIBUTION
     */
    public JSONObject getDistribution(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_DISTRIBUTION.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject;
    }

    /**
     * 商城配置:一级返佣比例
     * @return distribution
     */
    public String getDistribution_distribution(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_DISTRIBUTION.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("distribution");
    }

    /**
     * 商城配置:二级返佣比例
     * @return distributionTwo
     */
    public String getDistribution_distributionTwo(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_DISTRIBUTION.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("distributionTwo");
    }

    /**
     * 商城配置:最低提现金额
     * @return extractMoney
     */
    public String getDistribution_extractMoney(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_DISTRIBUTION.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("extractMoney");
    }

    /**
     * 商城配置:分销功能开关
     * @return extractMoney
     */
    public Integer getDistribution_enabled(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_DISTRIBUTION.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);

        return sysConfigDto.getEnabled();
    }
/*------------------------------------------------------------- 商城配置:(积分)  ------------------------------------------------------------------*/
    /**
     * 商城配置:(积分)
     * @return INTEGRAL
     */
    public JSONObject getIntegral(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_INTEGRAL.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject;
    }

    /**
     * 商城配置:积分抵用比率
     * @return convertibility
     */
    public BigDecimal getIntegral_convertibility(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_INTEGRAL.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getBigDecimal("convertibility");
    }

    /**
     * 商城配置:赠送积分比率
     * @return giveIntegral
     */
    public String getIntegral_giveIntegral(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_INTEGRAL.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("giveIntegral");
    }


    /*------------------------------------------------------------- 商城配置:签到  ------------------------------------------------------------------*/
    /**
     * 商城配置:签到
     * @return getSignconfig
     */
    public JSONArray getSignconfig(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_SIGNCONFIG.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getJSONArray("value");
    }

    /*------------------------------------------------------------- 商城配置:后端url配置  ------------------------------------------------------------------*/
    /**
     * 商城配置:后端配置信息
     * @return BASICSSHOP
     */
    public JSONObject getBasicsshop(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_BASICSSHOP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject;
    }
    /**
     * 商城配置:商城管理端地址
     * @return adminUrl
     */
    public String getBasicsshop_adminUrl(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_BASICSSHOP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("adminUrl");
    }
    /**
     * 商城配置:移动端api地址
     * @return appApiUrl
     */
    public String getBasicsshop_appApiUrl(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_BASICSSHOP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("appApiUrl");
    }
    /**
     * 商城配置:移动端H5地址
     * @return appH5Url
     */
    public String getBasicsshop_appH5Url(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_BASICSSHOP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("appH5Url");
    }
    /**
     * 商城配置:充值按钮 开关
     * @return goodsTransfeeCharge
     */
    public Integer getBasicsshop_rechargeOff(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_BASICSSHOP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getInt("rechargeOff");
    }

    /**
     * 商城配置:全场包邮设置 开关
     * @return goodsTransfeeCharge
     */
    public String getBasicsshop_goodsTransfeeCharge(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_BASICSSHOP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("goodsTransfeeCharge");
    }
    /**
     * 商城配置:全场满额包邮（元）
     * @return freight
     */
    public String getBasicsshop_freight(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_BASICSSHOP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("freight");
    }

    /**
     * 商城配置:商品LOG
     * @return shopLogo
     */
    public String getBasicsshop_shopLogo(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_BASICSSHOP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("shopLogo");
    }
    /*------------------------------------------------------------- 商城配置:腾讯地图key  ------------------------------------------------------------------*/
    /**
     * 商城配置:腾讯地图key
     * @return key
     */
    public String getMapKey_key(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_MAPKEY.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("key");
    }
    /*------------------------------------------------------------- 微信支付配置:微信支付配置  ------------------------------------------------------------------*/
    /**
     * 微信支付配置:微信支付配置
     * @return
     */
    public JSONObject getWeiXinPaySys(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_WEIXINPAYSYS.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject;
    }
    /**
     * 微信支付配置:微信APP支付ID
     * @return apyAppId
     */
    public String getWeiXinPaySys_apyAppId(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_WEIXINPAYSYS.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("apyAppId");
    }
    /**
     * 微信支付配置:商户ID
     * @return sellerId
     */
    public String getWeiXinPaySys_sellerId(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_WEIXINPAYSYS.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("sellerId");
    }
    /**
     * 微信支付配置:商户密钥
     * @return secretKey
     */
    public String getWeiXinPaySys_secretKey(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_WEIXINPAYSYS.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("secretKey");
    }
    /**
     * 微信支付配置:微信证书Url
     * @return Url
     */
    public String getWeiXinPaySys_url(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_WEIXINPAYSYS.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("payCertificateName");
    }
    /*------------------------------------------------------------- 微信支付service  ------------------------------------------------------------------*/

    /**
     * 微信支付service
     */
    public String getWooshopWeiXinPayService(){
        String wooshopWeixinPayService= WooshopConstants.WOOSHOP_WEIXIN_PAY_SERVICE;
        return  wooshopWeixinPayService+getExtendValue();
    }



    /*------------------------------------------------------------- 微信公众号配置  ------------------------------------------------------------------*/
    /**
     * 微信公众号配置:微信公众号配置
     * @return
     */
    public JSONObject getGonzonghaoSys(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_GONZONGHAOMP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject;
    }

    /**
     * 微信公众号配置:appId
     * @return
     */
    public String getGonzonghaoSys_appId(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_GONZONGHAOMP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("appId");
    }

    /**
     * 微信公众号配置:appSecret
     * @return
     */
    public String getGonzonghaoSys_appSecret(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_GONZONGHAOMP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("appSecret");
    }

    /**
     * 微信公众号配置:token
     * @return
     */
    public String getGonzonghaoSys_token(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_GONZONGHAOMP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("token");
    }

    /**
     * 微信公众号配置:encodingAESKey
     * @return
     */
    public String getGonzonghaoSys_encodingAESKey(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_GONZONGHAOMP.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
        return jsonObject.getStr("encodingAESKey");
    }


    /**
     * 微信公众号service
     */
    public String getWooshopGonzonghaoService(){
        String wooshopWeixinPayService= WooshopConstants.WOOSHOP_GONZHONGHAO_MP_SERVICE;
        return  wooshopWeixinPayService;
    }


    /**
     * 微信小程序service
     */
    public String getWooshopWeiXinService(){
        String wooshopWeixinPayService= WooshopConstants.WOOSHOP_WEIXIN_MP_SERVICE;
        return  wooshopWeixinPayService+getExtendValue();
    }

    /*------------------------------------------------------------- 获取PC端:首页轮播图  ------------------------------------------------------------------*/
    /**
     * 获取PC端:首页轮播图
     * @return
     */
    public List<Object>  getSwiperimg(){

        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_PC_HOME_SWIPERIMG.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());
//        JSONArray jsonArray=new JSONArray(sysConfigDto.getValue());
        return jsonObject.getJSONArray("menuList");
    }

    /*------------------------------------------------------------- 获取移动端:首页菜单  ------------------------------------------------------------------*/
    /**
     * 获取移动端:主页菜单
     * @return
     */
    public SysConfigHomeVo  getHomeMenu(){
        List<SysConfigHomeVo> sysAppHome = wooSysConfigService.getSysAppHome();
        String basicsshop_adminUrl = this.getBasicsshop_adminUrl();
        if ((sysAppHome.get(0).getMenu().getValue().indexOf("127.0.0.1:")!=-1||sysAppHome.get(0).getMenu().getValue().indexOf("localhost:")!=-1)&&profiles_active.equals("prod")){
            String url="http://127.0.0.1:8001";
            sysAppHome.get(0).getMenu().setValue(sysAppHome.get(0).getMenu().getValue().replace(url,basicsshop_adminUrl));
        }
        return sysAppHome.get(0);
    }

    /*------------------------------------------------------------- 用户个人中心:菜单  ------------------------------------------------------------------*/

    /**
     * 用户个人中心:菜单
     * @return
     */
    public JSONArray  getUserInfoMenu(){
        WooSysConfigQueryCriteria criteria =new WooSysConfigQueryCriteria();
        criteria.setMenuName(SysConfigEnum.WOOSHOP_SYSCONFIG_USER_INFO_MENU.toString());
        WooSysConfigDto sysConfigDto = wooSysConfigService.queryAll(criteria, Pageable.ofSize(2000).withPage(0)).getContent().get(0);
        String basicsshop_adminUrl = this.getBasicsshop_adminUrl();
        if ((sysConfigDto.getValue().indexOf("127.0.0.1:")!=-1||sysConfigDto.getValue().indexOf("localhost:")!=-1)&&profiles_active.equals("prod")){
            String url="http://127.0.0.1:8001";
            sysConfigDto.setValue(sysConfigDto.getValue().replace(url,basicsshop_adminUrl));
        }
        JSONObject jsonObject=new JSONObject(sysConfigDto.getValue());

        return jsonObject.getJSONArray("menuList");
    }

    //  管理后台获取主页参数
    public List<SysConfigHomeVo>  getSysAppHome(){
        List<SysConfigHomeVo> sysAppHome = wooSysConfigService.getSysAppHome();
        String basicsshop_adminUrl = this.getBasicsshop_adminUrl();
        if ((sysAppHome.get(0).getMenu().getValue().indexOf("127.0.0.1:")!=-1||sysAppHome.get(0).getMenu().getValue().indexOf("localhost:")!=-1)&&profiles_active.equals("prod")){
            String url="http://127.0.0.1:8001";
            sysAppHome.get(0).getMenu().setValue(sysAppHome.get(0).getMenu().getValue().replace(url,basicsshop_adminUrl));
        }
        return sysAppHome;
    }
    // 管理后台获取个人中心数据
    public PageResult<WooSysConfigDto>  getWooSysConfigs(WooSysConfigQueryCriteria criteria, Pageable pageable){
        PageResult<WooSysConfigDto> wooSysConfigDtoPageResult = wooSysConfigService.queryAll(criteria, pageable);
        if (criteria.getMenuName().equals(SysConfigEnum.WOOSHOP_SYSCONFIG_USER_INFO_MENU.toString())){
            String basicsshop_adminUrl = this.getBasicsshop_adminUrl();
            wooSysConfigDtoPageResult.getContent().forEach(item->{
                if ((item.getValue().indexOf("127.0.0.1:")!=-1||item.getValue().indexOf("localhost:")!=-1)&&profiles_active.equals("prod")){
                    item.setValue(item.getValue().replace("http://127.0.0.1:8001",basicsshop_adminUrl));
                }
            });
        }
        return wooSysConfigDtoPageResult;
    }
}
