package com.wooshop.modules.tools.storage.fastdfs.client;

import org.csource.fastdfs.ClientGlobal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * FastDFS配置
 */
@ConfigurationProperties(prefix = "fdfs")
public class FastDFSConfig {

    private static Logger logger = LoggerFactory.getLogger(FastDFSConfig.class);

    private String trackerServers;

    private String httpServer;//FastDFS Http服务器

    @PostConstruct
    public void init(){
        Properties props = new Properties();
        props.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, trackerServers);
        props.put(ClientGlobal.PROP_KEY_CONNECTION_POOL_ENABLED, true);
        try {
            //初始化
            ClientGlobal.initByProperties(props);
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    public String getTrackerServers() {
        return trackerServers;
    }

    public void setTrackerServers(String trackerServers) {
        this.trackerServers = trackerServers;
    }

    public String getHttpServer() {
        return httpServer;
    }

    public void setHttpServer(String httpServer) {
        this.httpServer = httpServer;
    }
}
