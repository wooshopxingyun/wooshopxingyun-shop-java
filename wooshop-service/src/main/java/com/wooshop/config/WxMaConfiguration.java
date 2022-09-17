package com.wooshop.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.google.common.collect.Maps;
import com.wooshop.constant.WooshopSysConfigConstant;
import com.wooshop.dozer.service.IGenerator;
import com.wooshop.modules.utils.RedisUache;
import com.wooshop.security.enums.ResultCode;
import com.wooshop.security.exception.ServiceException;
import com.wooshop.utils.RedisUtils;
import com.wooshop.utils.enums.WooshopConstants;
import me.chanjar.weixin.common.api.WxConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Configuration(proxyBeanMethods = false)
public class WxMaConfiguration {
    private static Map<String, WxMaService> maServices = Maps.newHashMap();
    private static Map<String, WxMaMessageRouter> routers = Maps.newHashMap();
    private static RedisUtils redisUtils;
    private static RedisUache redisUache;
    private static WxMaMessageHandler wxMaMessageHandler;
//    private static WxRestTemplateUtil wxRestTemplateUtil;
//    @Autowired
    private static RestTemplate restTemplate;
    private static IGenerator generator;


    public static WxMaMessageRouter getRouter(String appid) {
        return routers.get(appid);
    }


    @Autowired
    public WxMaConfiguration(RedisUtils redisUtils, RedisUache redisUache,RestTemplate restTemplate,IGenerator generator) {
        WxMaConfiguration.redisUtils = redisUtils;
        WxMaConfiguration.redisUache = redisUache;
//        WxMaConfiguration.wxRestTemplateUtil = wxRestTemplateUtil;
        WxMaConfiguration.restTemplate = restTemplate;
        WxMaConfiguration.generator = generator;
    }


    @PostConstruct
    public static WxMaService getWxMaService() {
        //微信公众号
        WxMaService wxMaService = maServices.get(redisUache.getWooshopWeiXinService());
        //增加一个redis标识
        if (wxMaService == null || redisUtils.get(redisUache.getWooshopWeiXinService()) == null) {
            WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
            config.setMsgDataFormat("JSON");
            config.setAppid(redisUache.getWechatMP_appId());//微信小程序:appId
            config.setSecret(redisUache.getWechatMP_appSecret());//微信小程序秘钥
            config.setToken(redisUache.getGonzonghaoSys_token());//公众号token
            config.setAesKey(redisUache.getGonzonghaoSys_encodingAESKey());//微信公众号配置:encodingAESKey
            wxMaService = new WxMaServiceImpl();
            wxMaService.setWxMaConfig(config);
            maServices.put(redisUache.getWooshopWeiXinService(), wxMaService);
            routers.put(redisUache.getWooshopWeiXinService(), newRouter(wxMaService));
            //增加标识
            redisUtils.set(redisUache.getWooshopWeiXinService(), "wooshop");

        }
        return wxMaService;
    }

    public static WcMiniAuthorizeVo getMiniAuthCode(String appId, String appSecret, String code) {
        String url = StrUtil.format(WooshopConstants.WECHAT_MINI_SNS_AUTH_CODE2SESSION_URL, appId, appSecret, code);
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.setContentType(MediaType.APPLICATION_JSON);
//
        HttpEntity<Map<String, Object>> requestEntity =
                new HttpEntity<>(headers);
        Object data = restTemplate.exchange( url, HttpMethod.GET, requestEntity, Object.class).getBody();
//        return url;
//        if (ObjectUtil.isNull(data)) {
//            throw new ServiceException(ResultCode.WECHAT_API_ERROR_RES_NOT);
//        }
//        if (data.containsKey("errcode") && !data.getStr("errcode").equals("0")) {
//            if (data.containsKey("errmsg")) {
//                throw new ServiceException(ResultCode.WECHAT_API_ERROR);
//            }
//        }
        return generator.convert(data, WcMiniAuthorizeVo.class);
    }


    /**
     * 移除WxMpService
     */
    public static void removeWxMaService() {
        redisUtils.del(redisUache.getWooshopWeiXinService());
        maServices.remove(redisUache.getWooshopWeiXinService());
        routers.remove(redisUache.getWooshopWeiXinService());
    }

    private static WxMaMessageRouter newRouter(WxMaService service) {
        final WxMaMessageRouter router = new WxMaMessageRouter(service);
        router.rule().handler(wxMaMessageHandler).next()
                .rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WooshopSysConfigConstant.BINDSTATECHANGE).handler(BINDSTATECHANGE_HANDLER).end();
        return router;
    }

    private static final WxMaMessageHandler BINDSTATECHANGE_HANDLER = (wxMessage, context, service, sessionManager) -> {
        wxMessage.getFromUser();
        wxMessage.getContent();
        return null;
    };


}

