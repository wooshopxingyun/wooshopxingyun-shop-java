package com.wooshop.security.enums;


/**
 * 客户端类型
 *
 * @author woo
 * @since 2022/05/8 9:46
 */

public enum ClientTypeEnum {

    /**
     * "移动端h5"
     */
    H5("移动端H5"),
    /**
     * "PC端"
     */
    PC("PC端"),
    /**
     * "小程序端"
     */
    WECHAT_MP("小程序端"),




    /**
     * "移动应用端"
     */
    APP("移动应用端"),
    APP_IOS("苹果"),
    APP_Android("安卓"),
    MP_WeiXin("微信小程序"),
    MP_WeiXinH5("微信公众号H5"),//正确是微信浏览器 H5
    MP_AliPay("支付宝小程序"),
    MP_Baidu("百度小程序"),
    MP_Toutiao("字节跳动小程序"),
    MP_KuaiShou("快手小程序"),
    MP_Qq("QQ小程序"),
    MP_360("360小程序"),
    QuickappWebview_Union("快应用联盟"),
    QuickappWebview_Huawei("快应用华为"),
    /**
     * "未知"
     */
    UNKNOWN("未知");

    private final String clientName;

    ClientTypeEnum(String des) {
        this.clientName = des;
    }

    public String clientName() {
        return this.clientName;
    }

    public String value() {
        return this.name();
    }
}
