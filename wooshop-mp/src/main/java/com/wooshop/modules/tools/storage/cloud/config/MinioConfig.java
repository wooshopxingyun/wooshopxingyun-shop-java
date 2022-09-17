package com.wooshop.modules.tools.storage.cloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Minio 文件存储
 *
 * @author fanglei
 * @date 2021/08/09
 **/
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private int partSize;

    private String bucketName;
    private String rootPath;
    private String domain;

}
