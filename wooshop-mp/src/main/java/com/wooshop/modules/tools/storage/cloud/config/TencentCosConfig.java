package com.wooshop.modules.tools.storage.cloud.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 腾讯COS云存储配置
 */
@Data
@ConfigurationProperties(prefix = "tencent.cos")
public class TencentCosConfig implements Serializable {

    private String domain;
    private Integer appId;
    private String secretId;
    private String secretKey;
    private String bucketName;
    private String region;
    private String rootPath;

}
