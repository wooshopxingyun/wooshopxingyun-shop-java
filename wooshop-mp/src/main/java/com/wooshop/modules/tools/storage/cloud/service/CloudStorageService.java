package com.wooshop.modules.tools.storage.cloud.service;

import cn.hutool.core.date.DateUtil;
import com.qcloud.cos.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Slf4j
public abstract class CloudStorageService {

    /**
     * 构建存储路径
     * @param rootPath 根目录
     * @param savePath 保存路径
     * @return 返回存储路径
     */
    protected String buildSavePath(String rootPath, String savePath) {
        //文件路径
        String path = DateUtil.format(new Date(), "yyyyMMdd");
        if(!StringUtils.isNullOrEmpty(rootPath)){
            path = rootPath + "/" + path;
        }
        if(savePath.startsWith("/")){
            return path + savePath;
        }else {
            return path + "/" + savePath;
        }
    }

    /**
     * 创建存储空间
     * @param bucketName
     */
    abstract void createBucket(String bucketName);

    /**
     * 删除存储空间
     * @param bucketName
     */
    abstract void deleteBucket(String bucketName);

    /**
     * 删除单个文件
     * @param fileUrl
     */
    public abstract void deleteFile(String fileUrl);

    /**
     * 批量删除多个文件
     * @param fileUrlList
     */
    public abstract void deleteFile(List<String> fileUrlList);

    /**
     * 判断文件是否存在
     * @param fileUrl 文件的URL
     * @return
     */
    public abstract boolean exist(String fileUrl);

    /**
     * 文件上传
     * @param data    文件字节数组
     * @param savePath    文件保存路径，包含文件名
     * @return        返回http访问地址
     */
    public abstract String upload(byte[] data, String savePath);

    /**
     * 上传网络文件
     * @param fileUrl 文件的网络地址
     * @param savePath 文件保存路径，包含文件名
     * @return 返回http访问地址
     */
    public String upload(String fileUrl,String savePath){
        try {
            InputStream inputStream = new URL(fileUrl).openStream();
            return upload(inputStream, savePath);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 文件上传
     * @param inputStream   字节流
     * @param savePath      文件保存路径，包含文件名
     * @return              返回http访问地址
     */
    public abstract String upload(InputStream inputStream, String savePath);

    /**
     * 文件上传
     * @param inputStream   字节流
     * @param savePath      文件保存路径，包含文件名
     * @param size      文件大小
     * @return              返回http访问地址
     */
    public String upload(InputStream inputStream, String savePath, long size) {
        return upload(inputStream, savePath);
    }
}
