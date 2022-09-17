
package com.wooshop.common.weixin.wxhendler;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.wooshop.modules.wechat_reply.domain.WooshopWechatReply;
import com.wooshop.modules.wechat_reply.service.WooshopWechatReplyService;
import com.wooshop.shopEvent.WechatTempateEnum;
import com.wooshop.utils.enums.WooshopConstants;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class SubscribeHandler extends AbstractHandler {

    @Autowired
    private WooshopWechatReplyService wechatReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {


        String str = WooshopConstants.WOOSHOP_UNIAPP_WEIXIN_WELCOME;
        WooshopWechatReply wooshopWechatReply = wechatReplyService.queryKey(WechatTempateEnum.WEIXIN_SUB_TYPE.getValue());
        if(!ObjectUtil.isNull(wooshopWechatReply)){
            str = JSONObject.parseObject(wooshopWechatReply.getReplyData()).getString("content");
        }

        try {
            WxMpXmlOutMessage msg= WxMpXmlOutMessage.TEXT()
                    .content(str)
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser())
                    .build();
            return msg;
        } catch (Exception e) {
//            this.logger.error(e.getMessage(), e);
//            log.error("退款失败,原因:{}", e.getMessage());
        }



        return null;
    }



}
