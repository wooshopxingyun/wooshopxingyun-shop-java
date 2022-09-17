package com.wooshop.modules.tools.storage.sftp.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 */
@ConfigurationProperties(prefix = "jsch.ssh2.server")
public class SSH2Config {

    /**
     * 服务器SSH连接IP地址
     */
    private String ip;

    /**
     * 服务器SSH连接端口号
     */
    private int port = 22;

    /**
     * 服务器登录用户名
     */
    private String userName;

    /**
     * 服务器登录密码
     */
    private String password;

    /**
     * SSH 客户端的 StrictHostKeyChecking 配置成no后，可以实现当第一次连接服务器时，自动接受新的公钥
     */
    private String strictHostKeyChecking = "no";

    /**
     * 存放上传文件根目录
     */
    private String workPath;

    /**
     * 文件服务器域名
     */
    private String domain;



    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStrictHostKeyChecking() {
        return strictHostKeyChecking;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setStrictHostKeyChecking(String strictHostKeyChecking) {
        this.strictHostKeyChecking = strictHostKeyChecking;
    }

    public String getWorkPath() {
        return workPath;
    }

    public void setWorkPath(String workPath) {
        this.workPath = workPath;
    }


}
