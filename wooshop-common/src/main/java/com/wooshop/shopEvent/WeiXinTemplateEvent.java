package com.wooshop.shopEvent;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class WeiXinTemplateEvent extends ApplicationEvent {

    private WeiXinTemplateBean weiXinTemplateBean;



    /**
     * 重写微信模板事件构造函数
     * @param source 发生事件的对象
     * @param weiXinTemplateBean 自定义微信模板
     */
    public WeiXinTemplateEvent(Object source,WeiXinTemplateBean weiXinTemplateBean) {
        super(source);
        this.weiXinTemplateBean = weiXinTemplateBean;
    }
}
