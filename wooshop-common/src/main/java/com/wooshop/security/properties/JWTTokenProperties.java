package com.wooshop.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * token过期配置
 *
 * @author woo
 * @version v4.0
 * @since 20202/06/14 16:20
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "woo.jwt-setting")
public class JWTTokenProperties {


    /**
     * token默认过期时间 (分钟)
     */
    private long tokenExpireTime = 6000;
}
