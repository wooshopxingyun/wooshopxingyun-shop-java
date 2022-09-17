package com.wooshop.utils.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
/*import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;*/
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.wooshop.constant.VerificationTypeEnum;
import com.wooshop.constant.WooshopSysConfigConstant;
import com.wooshop.security.cache.Cache;
import com.wooshop.security.enums.ResultCode;
import com.wooshop.security.exception.ServiceException;
import com.wooshop.utils.RedisUtils;
import com.wooshop.utils.SmsUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SmsUtilServiceImpl implements SmsUtilService {


    @Autowired
    private Cache cache;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void sendSmsCode(String phone){
        Object o1 = redisUtils.get(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_ALIYUNSMS_ENABLED);
        if (ObjectUtil.isNotNull(o1)){

        String code = RandomUtil.randomNumbers(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_SCODESIZE);//随机生成6位数字
        //存储得到redis
        //发送阿里云短信
        JSONObject json = new JSONObject();
        json.put("code",code);//生成的6位验证码放到json
        String accessKeyId = cache.getString(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_ALIYUNSMS_ACCESSKEY);
        String accessKeySecret = cache.getString(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_ALIYUNSMS_ACCESSKEYSECRET);
        String sign = cache.getString(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_ALIYUNSMS_SIGNNAME);
        String templateId = cache.getString(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_ALIYUNSMS_TEMPLATEID);
        String regionId = cache.getString(WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_ALIYUNSMS_REGION);
        DefaultProfile profile = DefaultProfile.getProfile(
                regionId,
                accessKeyId,
                accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();

        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", sign);
        request.putQueryParameter("TemplateCode", templateId);
        request.putQueryParameter("TemplateParam", json.toString());
        try{
            CommonResponse response = client.getCommonResponse(request);
            cache.put(WooshopSysConfigConstant.WOOSHOP_ALIYUNSMS+phone,code,WooshopSysConfigConstant.WOOSHOP_SYSCONFIG_TIME);
            System.out.println("发送短信验证码"+code);
        }catch (Exception e){
            log.error("发送短信错误",e);
        }

        }else {
            throw new ServiceException(ResultCode.VERIFICATION_EXIST);//短信服务没有开启
        }
    }

    @Override
    public boolean verifyCode(String phone, String code) {
        Object o = cache.get(WooshopSysConfigConstant.WOOSHOP_ALIYUNSMS + phone);
        if (o!=null&&o.equals(code)){
            //匹配对上同时删除redis中的缓存
            cache.remove(WooshopSysConfigConstant.WOOSHOP_ALIYUNSMS + phone);
            return true;
        }else{
            return false;
        }

    }
}
