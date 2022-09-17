package com.wooshop.modules.tools.storage.cloud.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 七牛KODO云存储配置
 */
@Data
@ConfigurationProperties(prefix = "qiniu.kodo")
public class QiniuKodoConfig implements Serializable {

    private String domain;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String rootPath;
}
