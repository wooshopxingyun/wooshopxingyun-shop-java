package com.wooshop.modules.tools.storage.cloud.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 阿里云OSS云存储配置
 */
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssConfig implements Serializable {

    private String domain;
    private String endPoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String rootPath;

}
