package com.wooshop.security.context;



import com.google.gson.Gson;
import com.wooshop.security.AuthUser;
import com.wooshop.security.cache.Cache;
import com.wooshop.security.enums.ResultCode;
import com.wooshop.security.enums.SecurityEnum;
import com.wooshop.security.exception.ServiceException;
import com.wooshop.security.token.SecretKeyUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户上下文
 *
 * @author woo
 * @version v4.0
 * @since 2020/11/14 20:27
 */
public class UserContext {

    /**
     * 根据request获取用户信息
     *
     * @return 授权用户
     */
    public static AuthUser getCurrentUser() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String accessToken = request.getHeader(SecurityEnum.HEADER_TOKEN.getValue());
            return getAuthUser(accessToken);
        }
        return null;
    }


    /**
     * 根据jwt获取token重的用户信息
     *
     * @param cache       缓存
     * @param accessToken token
     * @return 授权用户
     */
    public static AuthUser getAuthUser(Cache cache, String accessToken) {
        try {
            if (cache.getLikeKeys("*" + accessToken).size() == 0) {
                throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
            }
            return getAuthUser(accessToken);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据jwt获取token重的用户信息
     *
     * @param accessToken token
     * @return 授权用户
     */
    public static AuthUser getAuthUser(String accessToken) {
        try {
            //获取token的信息
            Claims claims
                    = Jwts.parser()
                    .setSigningKey(SecretKeyUtil.generalKeyByDecoders())//放入自定义秘钥
                    .parseClaimsJws(accessToken).getBody();
            //获取存储在claims中的用户信息  USER_CONTEXT("userContext")
            String json = claims.get(SecurityEnum.USER_CONTEXT.getValue()).toString(); //将枚举类值“userContext”通过claims 转换为string
            return new Gson().fromJson(json, AuthUser.class);//使用GSON是很方便进行解析的

        } catch (Exception e) {
            return null;
        }
    }
}
