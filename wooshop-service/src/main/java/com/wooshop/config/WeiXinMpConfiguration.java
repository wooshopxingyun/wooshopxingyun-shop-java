package com.wooshop.config;

import com.google.common.collect.Maps;
import com.wooshop.common.weixin.wxhendler.*;
import com.wooshop.modules.utils.RedisUache;
import com.wooshop.utils.RedisUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


import javax.annotation.Resource;
import java.util.Map;

/**
 * 微信公众号配置
 */
@Configuration(proxyBeanMethods = false)
public class WeiXinMpConfiguration {


    private static Map<String, WxMpService> mpServices = Maps.newHashMap();
    private static Map<String, WxMpMessageRouter> routers = Maps.newHashMap();

    private static LogHandler logHandler;
    private static NullHandler nullHandler;
    private static KfSessionHandler kfSessionHandler;
    private static StoreCheckNotifyHandler storeCheckNotifyHandler;
    private static LocationHandler locationHandler;
    private static MenuHandler menuHandler;
    private static MsgHandler msgHandler;
    private static UnsubscribeHandler unsubscribeHandler;
    private static SubscribeHandler subscribeHandler;
    private static ScanHandler scanHandler;
    private static RedisUtils redisUtils;//redis
    private static RedisUache redisUache;


    @Autowired
    public void WeiXinMpConfiguration(LogHandler logHandler, NullHandler nullHandler, KfSessionHandler kfSessionHandler,
                                      StoreCheckNotifyHandler storeCheckNotifyHandler, LocationHandler locationHandler,
                                      MenuHandler menuHandler, MsgHandler msgHandler, UnsubscribeHandler unsubscribeHandler,
                                      SubscribeHandler subscribeHandler, ScanHandler scanHandler,
                                      RedisUtils redisUtils,
                                      RedisUache redisUache){
        WeiXinMpConfiguration.logHandler = logHandler;
        WeiXinMpConfiguration.nullHandler = nullHandler;
        WeiXinMpConfiguration.kfSessionHandler = kfSessionHandler;
        WeiXinMpConfiguration.storeCheckNotifyHandler = storeCheckNotifyHandler;
        WeiXinMpConfiguration.locationHandler = locationHandler;
        WeiXinMpConfiguration.menuHandler = menuHandler;
        WeiXinMpConfiguration.msgHandler = msgHandler;
        WeiXinMpConfiguration.unsubscribeHandler = unsubscribeHandler;
        WeiXinMpConfiguration.subscribeHandler = subscribeHandler;
        WeiXinMpConfiguration.scanHandler = scanHandler;
        WeiXinMpConfiguration.redisUtils = redisUtils;
        WeiXinMpConfiguration.redisUache = redisUache;
    }


    /**
     * 获取WxMpService
     * @return
     */
    public static WxMpService getWxMpService() {
        String wooshopGonzonghaoService = redisUache.getWooshopGonzonghaoService();
        WxMpService wxMpService = mpServices.get(wooshopGonzonghaoService);
        //增加一个redis标识
        if(wxMpService == null || redisUtils.get(redisUache.getWooshopGonzonghaoService()) == null) {
            WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
            configStorage.setAppId(redisUache.getGonzonghaoSys_appId());
            configStorage.setSecret(redisUache.getGonzonghaoSys_appSecret());
            configStorage.setToken(redisUache.getGonzonghaoSys_token());
            configStorage.setAesKey(redisUache.getGonzonghaoSys_encodingAESKey());
            wxMpService = new WxMpServiceImpl();
            wxMpService.setWxMpConfigStorage(configStorage);
            mpServices.put(redisUache.getWooshopGonzonghaoService(), wxMpService);
            routers.put(redisUache.getWooshopGonzonghaoService(), newRouter(wxMpService));

            //增加标识
            redisUtils.set(redisUache.getWooshopGonzonghaoService(),"wooshop");
        }
        return wxMpService;
    }

    /**
     * 新增配置删除缓存
     * 移除WxMpService
     */
    public static void removeWxMpService(){
        redisUtils.del(redisUache.getWooshopGonzonghaoService());
        mpServices.remove(redisUache.getWooshopGonzonghaoService());
        routers.remove(redisUache.getWooshopGonzonghaoService());
    }

    /**
     *  获取WxMpMessageRouter
     */
    public static WxMpMessageRouter getWxMpMessageRouter() {
        WxMpMessageRouter wxMpMessageRouter = routers.get(redisUache.getWooshopGonzonghaoService());
        return wxMpMessageRouter;
    }

    private static WxMpMessageRouter newRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(logHandler).next();

        // 接收客服会话管理事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
                .handler(kfSessionHandler).end();
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
                .handler(kfSessionHandler)
                .end();
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
                .handler(kfSessionHandler).end();

        // 门店审核事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxMpEventConstants.POI_CHECK_NOTIFY)
                .handler(storeCheckNotifyHandler).end();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.CLICK).handler(menuHandler).end();

        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.VIEW).handler(menuHandler).end();

        // 扫码事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SCANCODE_WAITMSG).handler(menuHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE).handler(subscribeHandler)
                .end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.UNSUBSCRIBE)
                .handler(unsubscribeHandler).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.LOCATION).handler(locationHandler)
                .end();


        // 默认
        newRouter.rule().async(false).handler(msgHandler).end();

        return newRouter;
    }

}
