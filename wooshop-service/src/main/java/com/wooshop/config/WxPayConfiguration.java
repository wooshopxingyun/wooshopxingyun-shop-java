
package com.wooshop.config;


import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.google.common.collect.Maps;
import com.wooshop.modules.utils.RedisUache;
import com.wooshop.utils.RedisUtils;
import com.wooshop.utils.enums.MenuType;
import com.wooshop.utils.enums.PayWayEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 支付配置
 * @author woo
 * @date 2020/03/01
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@Service
public class WxPayConfiguration {

	private static Map<String, WxPayService> payServices = Maps.newHashMap();

	private static RedisUtils redisUtils;
	private static RedisUache redisUache;

	@Autowired
	public WxPayConfiguration(RedisUtils redisUtils,RedisUache redisUache) {
		WxPayConfiguration.redisUtils = redisUtils;
		WxPayConfiguration.redisUache = redisUache;
	}

	/**
	 *  获取getPayService
	 * @return
	 */
	public static WxPayService getPayService(PayWayEnum payWayEnum) {
		String wooshopWeiXinPayService = redisUache.getWooshopWeiXinPayService();
		WxPayService wxPayService = payServices.get(redisUache.getWooshopWeiXinPayService()+ payWayEnum.getValue());
		if(wxPayService == null || redisUtils.get(redisUache.getWooshopWeiXinPayService()) == null) {
			WxPayConfig payConfig = new WxPayConfig();
			switch (payWayEnum){
				case WECHATH5:
					 //* 微信公众号id
					payConfig.setAppId(redisUache.getGonzonghaoSys_appId());

					payConfig.setMchId(redisUache.getWeiXinPaySys_sellerId());//商户号
					payConfig.setMchKey(redisUache.getWeiXinPaySys_secretKey());//商户秘钥
					payConfig.setKeyPath(redisUache.getWeiXinPaySys_url());//商户证书地址
					break;
				case WXAPP:
					 //微信小程序id
					payConfig.setAppId(redisUache.getWechatMP_appId());
					if (redisUache.getWechatMP_wechatMpPay().equals(MenuType.IS_DEL_STATUS_1.getValue())){
						payConfig.setMchId(redisUache.getWechatMP_mchId());//独立商户号
						payConfig.setMchKey(redisUache.getWechatMP_secretKey());//独立商户号:商户秘钥
						payConfig.setKeyPath(redisUache.getWechatMP_url());//独立商户号:商户证书地址
					}else {
						payConfig.setMchId(redisUache.getWeiXinPaySys_sellerId());//商户号
						payConfig.setMchKey(redisUache.getWeiXinPaySys_secretKey());//商户秘钥
						payConfig.setKeyPath(redisUache.getWeiXinPaySys_url());//商户证书地址
					}
					break;
				case APP:
					 //支付appId
					payConfig.setAppId(redisUache.getWeiXinPaySys_apyAppId());

					payConfig.setMchId(redisUache.getWeiXinPaySys_sellerId());//商户号
					payConfig.setMchKey(redisUache.getWeiXinPaySys_secretKey());//商户秘钥
					payConfig.setKeyPath(redisUache.getWeiXinPaySys_url());//商户证书地址
					break;
				case PC:
					 //支付appId
//					payConfig.setAppId(redisUache.getWeiXinPaySys_apyAppId());

					payConfig.setAppId(redisUache.getGonzonghaoSys_appId());//公众号id
					payConfig.setMchId(redisUache.getWeiXinPaySys_sellerId());//商户号
					payConfig.setMchKey(redisUache.getWeiXinPaySys_secretKey());//商户秘钥
					payConfig.setKeyPath(redisUache.getWeiXinPaySys_url());//商户证书地址
					break;
				default:
			}

			// 可以指定是否使用沙箱环境
			payConfig.setUseSandboxEnv(false);
			wxPayService = new WxPayServiceImpl();
			wxPayService.setConfig(payConfig);
			payServices.put(redisUache.getWooshopWeiXinPayService()+ payWayEnum.getValue(), wxPayService);

			//增加标识
			redisUtils.set(redisUache.getWooshopWeiXinPayService(),"wooshop");
		}
		return wxPayService;
    }


	/**
	 * 移除delWeiXinPayService
	 */
	public static void delWeiXinPayService(){
		redisUtils.del(redisUache.getWooshopWeiXinPayService());
		payServices.remove(redisUache.getWooshopWeiXinPayService());
	}

}
