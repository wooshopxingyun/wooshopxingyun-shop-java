package com.wooshop.modules.tools.storage.cloud.service;

import com.wooshop.modules.tools.storage.cloud.config.MinioConfig;
import io.minio.*;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import com.wooshop.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Minio 文件服务
 *
 * @author fanglei
 * @date 2021/08/09
 **/
@Slf4j
public class MinioStorageService extends CloudStorageService {

    @Autowired
    private MinioConfig minioConfig;

    @Override
    void createBucket(String bucketName) {
        MinioClient minioClient = getMinioClient();
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("创建Bucket失败", e);
        }
    }

    @Override
    void deleteBucket(String bucketName) {
        MinioClient minioClient = getMinioClient();
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException("删除Bucket失败", e);
        }
    }
    /**
     * 通过文件URL反向解析文件保存路径
     *
     * @param fileUrl 文件URL
     * @return 文件保存路径
     */
    public String getSavePath(String fileUrl) {
        return fileUrl.substring(minioConfig.getDomain().length() + 1);
    }

    @Override
    public void deleteFile(String fileUrl) {
        MinioClient minioClient = getMinioClient();
        try {
            String savePath = getSavePath(fileUrl);
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(savePath).build());
        } catch (Exception e) {
            throw new RuntimeException("删除File失败", e);
        }
    }

    @Override
    public void deleteFile(List<String> fileUrlList) {
        MinioClient minioClient = getMinioClient();
        try {
            List<DeleteObject> keys = new ArrayList<>();
            fileUrlList.forEach(item-> keys.add(new DeleteObject(getSavePath(item))));
            minioClient.removeObjects(RemoveObjectsArgs.builder()
                    .bucket(minioConfig.getBucketName()).objects(keys)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("批量删除File失败", e);
        }
    }

    @Override
    public boolean exist(String fileUrl) {
        String objectName = getSavePath(fileUrl);
        MinioClient minioClient = getMinioClient();
        try {
            GetObjectResponse object = minioClient.getObject(GetObjectArgs.builder().bucket(minioConfig.getBucketName())
                    .object(objectName).build());
            if(StringUtils.isNotEmpty(object.object())) {
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("判断文件是否存在", e);
        }
        return false;
    }

    @Override
    public String upload(byte[] data, String savePath) {
        return upload(new ByteArrayInputStream(data), savePath, data.length);
    }

    @Deprecated
    @Override
    public String upload(InputStream inputStream, String savePath) {
        return null;
    }

    @Override
    public String upload(InputStream inputStream, String savePath, long size) {
        MinioClient minioClient = getMinioClient();
        try {
            savePath = buildSavePath(minioConfig.getRootPath(), savePath);
            minioClient.putObject(PutObjectArgs.builder().bucket(minioConfig.getBucketName())
                    .object(savePath).stream(inputStream, size, minioConfig.getPartSize()).build());
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败", e);
        }
        return minioConfig.getDomain() + "/" + savePath;//返回文件的访问URL地址
    }

    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioConfig.getEndpoint())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
    }
}
