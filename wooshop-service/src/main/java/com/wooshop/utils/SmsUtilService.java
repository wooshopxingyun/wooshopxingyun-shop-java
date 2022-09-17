package com.wooshop.utils;


import com.wooshop.constant.VerificationTypeEnum;

/**
 * 短信验证接口
 */
public interface SmsUtilService {

    /**
     * 发送验证码
     * @param phone 手机号码
     * @param verificationTypeEnum 验证码类型
     */
    void sendSmsCode(String phone);


    /**
     * 验证验证码
     * @param phone 手机号码
     * @param code 接收到的验证码
     * @return
     */
    public boolean verifyCode(String phone,String code);

}
