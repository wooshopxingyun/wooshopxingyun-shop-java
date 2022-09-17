package com.wooshop.modules.tools.storage.cloud.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.wooshop.modules.tools.storage.cloud.config.TencentCosConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯COS云存储服务
 *
 */
@Slf4j
public class TencentCosStorageService extends CloudStorageService {

    private COSClient cosClient;

    private TencentCosConfig config;

    public TencentCosStorageService(TencentCosConfig config){
        this.config = config;
        //初始化
        init();
    }

    private void init(){
        COSCredentials credentials = new BasicCOSCredentials(config.getSecretId(), config.getSecretKey());
        //初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        Region region = new Region(config.getRegion());
        //设置bucket所在的区域，华南：gz 华北：tj 华东：sh
        clientConfig.setRegion(region);
        cosClient = new COSClient(credentials,clientConfig);
    }

    /**
     * 创建存储空间
     * 创建存储桶是低频操作，一般建议在控制台创建 Bucket，在 SDK 进行 Object 的操作
     * @param bucketName
     */
    @Override
    void createBucket(String bucketName) {
        String bucket = bucketName+"-"+config.getAppId(); //存储桶名称，格式：BucketName-APPID
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
        // 设置 bucket 的权限为公有读私有写
        createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        Bucket bucketResult = cosClient.createBucket(createBucketRequest);
    }

    /**
     * 删除存储空间
     *
     * @param bucketName
     */
    @Override
    void deleteBucket(String bucketName) {
        String bucket = bucketName+"-"+config.getAppId(); //存储桶名称，格式：BucketName-APPID
        cosClient.deleteBucket(bucket);
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
        String savePath = getSavePath(fileUrl);
        cosClient.deleteObject(config.getBucketName(),savePath);
    }

    /**
     * 批量删除多个文件
     *
     * @param fileUrlList
     */
    @Override
    public void deleteFile(List<String> fileUrlList) {
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(config.getBucketName());
        // 设置要删除的key列表, 最多一次删除1000个
        ArrayList<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<>();
        fileUrlList.forEach(item-> keyList.add(new DeleteObjectsRequest.KeyVersion(getSavePath(item))));
        // 传入要删除的文件名
        deleteObjectsRequest.setKeys(keyList);
        // 批量删除文件
        try {
            DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
            List<DeleteObjectsResult.DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();
        } catch (MultiObjectDeleteException mde) { // 如果部分删除成功部分失败, 返回MultiObjectDeleteException
            List<DeleteObjectsResult.DeletedObject> deleteObjects = mde.getDeletedObjects();
            List<MultiObjectDeleteException.DeleteError> deleteErrors = mde.getErrors();
        } catch (CosServiceException e) { // 如果是其他错误，例如参数错误， 身份验证不过等会抛出 CosServiceException
            throw e;
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
        ObjectMetadata objectMetadata = cosClient.getObjectMetadata(config.getBucketName(), objectName);
        return objectMetadata != null;
    }

    @Override
    public String upload(byte[] data, String savePath) {
        return upload(new ByteArrayInputStream(data), savePath);
    }

    @Override
    public String upload(InputStream inputStream, String savePath) {
        try {
            long size = inputStream.available();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 从输入流上传必须制定content length
            // 否则http客户端可能会缓存所有数据，存在内存OOM的情况
            objectMetadata.setContentLength(size);
            savePath = buildSavePath(config.getRootPath(),savePath);
            PutObjectRequest putObjectRequest = new PutObjectRequest(config.getBucketName(), savePath, inputStream, objectMetadata);
            // 设置存储类型, 默认是标准(Standard), 低频(standard_ia), 近线(nearline)
            putObjectRequest.setStorageClass(StorageClass.Standard);
            //将图片上传到 COS
            cosClient.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return config.getDomain()+"/"+ savePath;
    }
}
