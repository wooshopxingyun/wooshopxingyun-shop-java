package com.wooshop.modules.tools.storage.cloud.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.wooshop.modules.tools.storage.cloud.config.AliyunOssConfig;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 阿里OSS云存储服务
 *
 */
@Slf4j
public class AliyunOssStorageService extends CloudStorageService {

    private AliyunOssConfig config;

    public AliyunOssStorageService(AliyunOssConfig config){
        this.config = config;
    }

    /**
     * 创建存储空间
     *
     * @param bucketName
     */
    @Override
    void createBucket(String bucketName) {
        // 创建OSSClient实例
        OSS ossClient = getOSSClient();
        try {
            boolean exists = ossClient.doesBucketExist(bucketName);//判断存储空间是否存在
            if (exists) {
                return;
            }
            // 创建CreateBucketRequest对象。
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            // 创建存储空间。
            ossClient.createBucket(createBucketRequest);
        }catch (Exception e) {
            throw new RuntimeException("创建Bucket失败", e);
        } finally {
            if(ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }

    }

    /**
     * 删除存储空间
     * 删除存储空间之前，必须先删除存储空间下的所有文件、LiveChannel和分片上传产生的碎片。
     *
     * @param bucketName
     */
    @Override
    void deleteBucket(String bucketName) {
        // 创建OSSClient实例
        OSS ossClient = getOSSClient();
        try {
            // 删除存储空间。
            ossClient.deleteBucket(bucketName);
        } catch (Exception e) {
            throw new RuntimeException("删除Bucket失败", e);
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }


    }

    /**
     * 通过文件URL反向解析文件保存路径
     *
     * @param fileUrl 文件URL
     * @return 文件保存路径
     */
    public String getSavePath(String fileUrl) {
        return fileUrl.substring(config.getDomain().length()+1);
    }

    /**
     * 删除单个文件
     *
     * @param fileUrl
     */
    @Override
    public void deleteFile(String fileUrl) {
        // 创建OSSClient实例
        OSS ossClient = getOSSClient();
        try {
            String savePath = getSavePath(fileUrl);
            ossClient.deleteObject(config.getBucketName(), savePath);
        } catch (Exception e) {
            throw new RuntimeException("删除File失败", e);
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }

    }

    /**
     * 批量删除多个文件
     *
     * @param fileUrlList
     */
    @Override
    public void deleteFile(List<String> fileUrlList) {
        // 创建OSSClient实例
        OSS ossClient = getOSSClient();
        try {
            List<String> keys = new ArrayList<>();
            fileUrlList.forEach(item -> keys.add(getSavePath(item)));
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(config.getBucketName()).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        } catch (Exception e) {
            throw new RuntimeException("批量删除文件失败", e);
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param fileUrl 文件的URL
     * @return
     */
    @Override
    public boolean exist(String fileUrl) {
        String objectName = getSavePath(fileUrl);
        // 创建OSSClient实例
        OSS ossClient = getOSSClient();
        boolean exist = false;
        try {
            // 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；如果为false，则考虑302重定向或镜像。
            exist = ossClient.doesObjectExist(config.getBucketName(), objectName);
        } catch (Exception e) {
            if (ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
        //  返回是否存在
        return exist;
    }

    @Override
    public String upload(byte[] data, String savePath) {
        return upload(new ByteArrayInputStream(data), savePath);
    }

    @Override
    public String upload(InputStream inputStream, String savePath) {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(config.getEndPoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        try {
            savePath = buildSavePath(config.getRootPath(),savePath);
            //上传文件到oss
            ossClient.putObject(config.getBucketName(), savePath, inputStream);
        } catch (Exception e){
            throw new RuntimeException("上传文件失败，请检查配置信息", e);
        }finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return config.getDomain() + "/" + savePath;//返回文件的访问URL地址
    }

    public OSS getOSSClient() {
        return new OSSClientBuilder().build(config.getEndPoint(), config.getAccessKeyId(), config.getAccessKeySecret());
    }
}
