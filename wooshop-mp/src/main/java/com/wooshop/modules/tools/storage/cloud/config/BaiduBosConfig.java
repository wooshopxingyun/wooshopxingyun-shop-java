package com.wooshop.modules.tools.storage.cloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 百度云Bos存储配置
 *
 * @author fanglei
 * @date 2021/08/05 23:29
 **/
@Data
@ConfigurationProperties(prefix = "baidu.bos")
public class BaiduBosConfig implements Serializable {
    private String accessKeyId;
    private String accessKeySecret;
    private String domain;
    // 用户自己指定的域名
    private String endPoint;
    private String bucketName;
    private String rootPath;
}
