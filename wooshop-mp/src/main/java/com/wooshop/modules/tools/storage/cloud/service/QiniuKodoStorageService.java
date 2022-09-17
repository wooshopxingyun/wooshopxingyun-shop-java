package com.wooshop.modules.tools.storage.cloud.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import com.wooshop.modules.tools.storage.cloud.config.QiniuKodoConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 七牛Kodo云存储服务
 *
 */
@Slf4j
public class QiniuKodoStorageService extends CloudStorageService {

    private UploadManager uploadManager;
    private BucketManager bucketManager;
    private String token;

    private QiniuKodoConfig config;

    public QiniuKodoStorageService(QiniuKodoConfig config){
        this.config = config;
        //初始化
        init();
    }

    private void init(){
        uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
        token = Auth.create(config.getAccessKey(),config.getSecretKey()).uploadToken(config.getBucketName());

        Auth auth = Auth.create(config.getAccessKey(),config.getSecretKey());
        bucketManager = new BucketManager(auth, new Configuration(Region.autoRegion()));
    }

    /**
     * 创建存储空间
     *
     * @param bucketName
     */
    @Override
    void createBucket(String bucketName) {
        //todo 暂无实现
    }

    /**
     * 删除存储空间
     *
     * @param bucketName
     */
    @Override
    void deleteBucket(String bucketName) {
        //todo 暂无实现
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
        String objectName = getSavePath(fileUrl);
        try {
            bucketManager.delete(config.getBucketName(), objectName);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            throw new RuntimeException(ex);
        }
    }

    /**
     * 批量删除多个文件
     *
     * @param fileUrlList
     */
    @Override
    public void deleteFile(List<String> fileUrlList) {
        try {
            //单次批量请求的文件数量不得超过1000
            List<String> keyList = new ArrayList<>();
            fileUrlList.forEach(item-> keyList.add(getSavePath(item)));
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            String[] keyArr = new String[keyList.size()];
            batchOperations.addDeleteOp(config.getBucketName(), keyList.toArray(keyArr));
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < keyArr.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = keyArr[i];
                System.out.print(key + "\t");
                if (status.code == 200) {
                    System.out.println("delete success");
                } else {
                    System.out.println(status.data.error);
                }
            }
        } catch (QiniuException ex) {
            throw new RuntimeException(ex);
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
        try {
            FileInfo fileInfo = bucketManager.stat(config.getBucketName(), objectName);
            return fileInfo != null;
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String upload(byte[] data, String savePath) {
        try {
            savePath = buildSavePath(config.getRootPath(),savePath);
            Response res = uploadManager.put(data, savePath, token);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败，请核对七牛配置信息", e);
        }

        return config.getDomain() + "/" + savePath;
    }

    @Override
    public String upload(InputStream inputStream, String savePath) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, savePath);
        } catch (IOException e) {
            throw new RuntimeException("上传文件失败", e);
        }
    }
}
