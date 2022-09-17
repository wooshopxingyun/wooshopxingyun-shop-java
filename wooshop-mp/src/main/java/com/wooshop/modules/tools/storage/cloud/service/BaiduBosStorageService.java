package com.wooshop.modules.tools.storage.cloud.service;

import com.baidubce.Protocol;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.CreateBucketRequest;
import com.baidubce.services.bos.model.DeleteMultipleObjectsRequest;
import com.baidubce.services.bos.model.DeleteMultipleObjectsResponse;
import com.wooshop.modules.tools.storage.cloud.config.BaiduBosConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 百度云BOS储存
 *
 * @author fanglei
 * @date 2021/08/05 23:28
 **/
public class BaiduBosStorageService extends CloudStorageService {

    @Autowired
    private BaiduBosConfig baiduBosConfig;

    private BosClient getClient() {
        BosClientConfiguration config = new BosClientConfiguration();
        // 设置最大连接数为HTTP10
        config.setMaxConnections(10);
        // 设置连接超时为毫秒TCP5000
        config.setConnectionTimeoutInMillis(5000);
        // 设置传输数据超时的时间为毫秒Socket2000
        config.setSocketTimeoutInMillis(2000);
        //创建客户端bos
        config.setCredentials(new DefaultBceCredentials(baiduBosConfig.getAccessKeyId(), baiduBosConfig.getAccessKeySecret()));
        config.setEndpoint(baiduBosConfig.getEndPoint());		//指定所属区域
        config.setProtocol(Protocol.HTTPS);	//如果不指明，则使用http
        return new BosClient(config);
    }

    @Override
    void createBucket(String bucketName) {
        BosClient client = getClient();
        try {
            boolean exists = client.doesBucketExist(bucketName);
            if(exists) {
                return;
            }
            // 创建CreateBucketRequest对象。
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            // 创建存储空间。
            client.createBucket(createBucketRequest);
        }catch (Exception e) {
            throw new RuntimeException("创建Bucket失败", e);
        } finally {
            // 关闭BOSClient。
            client.shutdown();
        }
    }

    @Override
    void deleteBucket(String bucketName) {
        BosClient client = getClient();
        try {
            // 删除存储空间。
            client.deleteBucket(bucketName);
        }catch (Exception e) {
            throw new RuntimeException("删除Bucket失败", e);
        } finally {
            // 关闭BOSClient。
            client.shutdown();
        }
    }
    /**
     * 通过文件URL反向解析文件保存路径
     *
     * @param fileUrl 文件URL
     * @return 文件保存路径
     */
    public String getSavePath(String fileUrl) {
        return fileUrl.substring(baiduBosConfig.getDomain().length()+1);
    }

    @Override
    public void deleteFile(String fileUrl) {
        BosClient bosClient = getClient();
        try {
            String savePath = getSavePath(fileUrl);
            bosClient.deleteObject(baiduBosConfig.getBucketName(), savePath);
        }catch (Exception e) {
            throw new RuntimeException("删除文件失败", e);
        } finally {
            // 关闭BOSClient。
            bosClient.shutdown();
        }
    }

    @Override
    public void deleteFile(List<String> fileUrlList) {
        BosClient bosClient = getClient();
        try {
            List<String> keys = new ArrayList<>();
            fileUrlList.forEach(item -> keys.add(getSavePath(item)));
            DeleteMultipleObjectsRequest deleteMultipleObjectsRequest = new DeleteMultipleObjectsRequest()
                    .withBucketName(baiduBosConfig.getBucketName());
            deleteMultipleObjectsRequest.setObjectKeys(keys);
            DeleteMultipleObjectsResponse deleteObjectsResult = bosClient.deleteMultipleObjects(deleteMultipleObjectsRequest);
        }catch (Exception e) {
            throw new RuntimeException("批量删除文件", e);
        } finally {
            // 关闭BOSClient。
            bosClient.shutdown();
        }

    }

    @Override
    public boolean exist(String fileUrl) {
        String objectName = getSavePath(fileUrl);
        BosClient bosClient = getClient();
        boolean exists = false;
        try {
            exists = bosClient.doesObjectExist(baiduBosConfig.getBucketName(), objectName);
        }catch (Exception e) {
            throw new RuntimeException("判断文件是否存在", e);
        } finally {
            bosClient.shutdown();
        }

        return exists;
    }

    @Override
    public String upload(byte[] data, String savePath) {
        return upload(new ByteArrayInputStream(data), savePath);
    }

    @Override
    public String upload(InputStream inputStream, String savePath) {
        BosClient bosClient = getClient();
        try {
            savePath = buildSavePath(baiduBosConfig.getRootPath(), savePath);
            //上传文件到bos
            bosClient.putObject(baiduBosConfig.getBucketName(), savePath, inputStream);
        } catch (Exception e){
            throw new RuntimeException("上传文件失败，请检查配置信息", e);
        } finally {
            bosClient.shutdown();
        }
        return baiduBosConfig.getDomain() + "/" + savePath;//返回文件的访问URL地址
    }
}
