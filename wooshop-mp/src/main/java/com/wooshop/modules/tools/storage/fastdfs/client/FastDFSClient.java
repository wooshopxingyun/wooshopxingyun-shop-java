package com.wooshop.modules.tools.storage.fastdfs.client;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.digest.MD5;
import com.wooshop.modules.tools.storage.fastdfs.bean.FastDFSFile;
import com.wooshop.modules.tools.storage.fastdfs.bean.FastDFSUploadResult;
import com.wooshop.modules.tools.storage.fastdfs.exception.ERRORS;
import com.wooshop.modules.tools.storage.fastdfs.exception.FastDFSException;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.*;


/**
 * @ClassName: FastDFSClient
 * @Description: fastdfs文件操作,实现文件的上传与下载、删除;
 * @author 孤傲苍狼 290603672@qq.com
 * @date 2017年10月19日 上午10:21:46
 *
 */
public class FastDFSClient {

    private static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);

    private FastDFSConfig fastDFSConfig;

    public FastDFSClient(FastDFSConfig fastDFSConfig){
        this.fastDFSConfig = fastDFSConfig;
    }

    public FastDFSConfig getFastDFSConfig() {
        return fastDFSConfig;
    }

    /**
     * 上传文件,传file
     * @param file 要上传的文件
     * @return null为失败
     */
    public String uploadFile(File file) throws FastDFSException {
        try {
            String fileName =  file.getName();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();// 后缀名
            byte[] fileContent = FileUtil.readBytes(file);
            return uploadFile(fileContent,ext);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 上传文件,传file
     * @param file 要上传的文件
     * @param extName 文件的扩展名，不包含（.） 如 txt  jpg png 等
     * @return null为失败
     */
    public String uploadFile(File file,String extName) throws FastDFSException {
        try {
            byte[] fileContent = FileUtil.readBytes(file);
            return uploadFile(fileContent,extName);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 上传文件,传file
     * @param file 要上传的文件
     * @param metas 文件扩展信息
     * @return null为失败
     */
    public String uploadFile(File file, NameValuePair[] metas) throws FastDFSException {
        try {
            String fileName =  file.getName();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();// 后缀名
            byte[] fileContent = FileUtil.readBytes(file);
            return uploadFile(fileContent,ext,metas);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 上传文件,传file
     * @param file 要上传的文件
     * @param metas 文件扩展信息
     * @return null为失败
     */
    public String uploadFile(File file,String extName, NameValuePair[] metas) throws FastDFSException {
        try {
            byte[] fileContent = FileUtil.readBytes(file);
            return uploadFile(fileContent,extName,metas);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 上传文件,传fileName
     * @param fileName 文件的磁盘路径名称 如：D:/image/aaa.jpg
     * @return null为失败
     */
    public String uploadFile(String fileName) throws FastDFSException {
        return uploadFile(fileName, null, null);
    }
    /**
     *
     * @param fileName 文件的磁盘路径名称 如：D:/image/aaa.jpg
     * @param extName 文件的扩展名，不包含（.） 如 txt jpg等
     * @return null为失败
     */
    public String uploadFile(String fileName, String extName) throws FastDFSException {
        return uploadFile(fileName, extName, null);
    }

    /**
     * 上传文件
     * @param fileContent  文件的字节数组
     * @param extName  文件的扩展名，不包含（.） 如 txt  jpg png 等
     * @return fileHttpAccessUrl 可以直接通过Http访问的文件URL,返回null则表示上传失败
     */
    public String uploadFile(byte[] fileContent, String extName) throws FastDFSException {
        return uploadFile(fileContent, extName, null);
    }

    /**
     * 上传文件
     * @param fileName 文件全路径
     * @param extName 文件的扩展名，不包含（.） 如 txt  jpg png 等
     * @param metas 文件扩展信息
     * @return fileHttpAccessUrl 可以直接通过Http访问的文件URL,返回null则表示上传失败
     */
    public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws FastDFSException {
        String logId = UUID.randomUUID().toString();
        TrackerServer trackerServer;
        String uploadResults = null;
        try {
            TrackerClient trackerClient = new TrackerClient();
            /** 获取fastdfs服务器连接 */
            trackerServer = trackerClient.getTrackerServer();
            StorageServer storageServer = null;
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            //上传文件
            uploadResults = storageClient.upload_file1(fileName, extName, metas);
        } catch (IOException e) {
            logger.error("IO Exception when uploading the file: " + fileName, e);
        } catch (Exception e) {
            logger.error("[上传文件（upload)][" + logId + "][异常：" + e + "]");
            throw ERRORS.SYS_ERROR.ERROR();
        }
        if (uploadResults == null) {
            /** 文件系统上传返回结果错误 */
            throw ERRORS.UPLOAD_RESULT_ERROR.ERROR();
        }
        String fileHttpAccessUrl = fastDFSConfig.getHttpServer()+"/" + uploadResults;
        return fileHttpAccessUrl;
    }

    /**
     * FastDFS上传文件
     * @param fastDFSFile FastDFS文件
     * @return fileAbsolutePath 文件网络URL地址，可以使用Http方式访问
     */
    public String uploadFile(FastDFSFile fastDFSFile) throws FastDFSException {
        String logId = UUID.randomUUID().toString();
        TrackerServer trackerServer;
        String[] uploadResults = null;
        try {
            NameValuePair[] meta_list = null;
            Map<String,String> fileMetaMap = fastDFSFile.getFileMetaMap();
            if(fileMetaMap != null && fileMetaMap.size()>0) {
                int i= 0;
                //FastDFS文件上传时可以使用NameValuePair记录一下文件的额外信息
                meta_list = new NameValuePair[fileMetaMap.size()];
                for (Map.Entry<String, String> entry : fileMetaMap.entrySet()) {
                    meta_list[i] = new NameValuePair(entry.getKey(), entry.getValue());
                    i++;
                }
            }
            TrackerClient trackerClient = new TrackerClient();
            /** 获取fastdfs服务器连接 */
            trackerServer = trackerClient.getTrackerServer();
            StorageServer storageServer = null;
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            uploadResults = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);
            logger.info("[上传文件（upload）-fastdfs服务器响应结果][" + logId + "][result：results=" + uploadResults[0] + "/"+uploadResults[1]+"]");
        } catch (IOException e) {
            logger.error("IO Exception when uploadind the fastDFSFile: " + fastDFSFile.getName(), e);
        } catch (Exception e) {
            logger.error("[上传文件（upload)][" + logId + "][异常：" + e + "]");
            throw ERRORS.SYS_ERROR.ERROR();
        }

        if (uploadResults == null) {
            /** 文件系统上传返回结果错误 */
            throw ERRORS.UPLOAD_RESULT_ERROR.ERROR();
        }
        /** uploadResults[0]:组名，uploadResults[1]:远程文件名 */
        String groupName 		= uploadResults[0];
        String remoteFileName   = uploadResults[1];
        fastDFSFile.setGroup(groupName);
        fastDFSFile.setStoragePath(remoteFileName);
        fastDFSFile.setFileId(groupName+"/"+remoteFileName);
        String fileAbsolutePath = fastDFSConfig.getHttpServer()+"/" + groupName +"/" + remoteFileName;
        fastDFSFile.setRemotePath(fileAbsolutePath);
        return fileAbsolutePath;
    }

    /**
     * 上传文件
     * @param fileContent 文件的内容，字节数组
     * @param extName 文件的扩展名，不包含（.） 如 txt  jpg png 等
     * @param metas 文件扩展信息
     * @return fileHttpAccessUrl 可以直接通过Http访问的文件URL,返回null则表示上传失败
     */
    public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws FastDFSException {
        String logId = UUID.randomUUID().toString();
        /** 获取fastdfs服务器连接 */
        TrackerServer trackerServer;
        String uploadResult = null;
        try {
            TrackerClient trackerClient = new TrackerClient();
            /** 获取fastdfs服务器连接 */
            trackerServer = trackerClient.getTrackerServer();
            StorageServer storageServer = null;
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            //上传文件
            uploadResult = storageClient.upload_file1(fileContent, extName, metas);
        } catch (IOException e) {
            logger.error("Non IO Exception when uploading the file: ", e);
        } catch (Exception e) {
            logger.error("[上传文件（upload)][" + logId + "][异常：" + e + "]");
            throw ERRORS.SYS_ERROR.ERROR();
        }
        if (uploadResult == null) {
            /** 文件系统上传返回结果错误 */
            throw ERRORS.UPLOAD_RESULT_ERROR.ERROR();
        }
        return fastDFSConfig.getHttpServer()+"/" + uploadResult;
    }

    /**
     * 批量上传
     * @param inputStreamMap 需要上传的文件集合
     * @return List<FastDFSUploadResult>
     */
    public List<FastDFSUploadResult> uploadFile(Map<String, InputStream> inputStreamMap) throws FastDFSException {
        String logId = UUID.randomUUID().toString();
        List<FastDFSUploadResult> uploadResultList = new ArrayList<>();
        /** 获取fastdfs服务器连接 */
        TrackerServer trackerServer;
        try {
            TrackerClient trackerClient = new TrackerClient();
            /** 获取fastdfs服务器连接 */
            trackerServer = trackerClient.getTrackerServer();
            StorageServer storageServer = null;
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            for(Map.Entry<String,InputStream> entry:inputStreamMap.entrySet()){
                FastDFSUploadResult fastDFSUploadResult = new FastDFSUploadResult();
                long startTime = System.currentTimeMillis();
                String fileName = entry.getKey();//文件名
                InputStream inputStream = entry.getValue();
                byte[] fileContent = IoUtil.readBytes(inputStream);
                String extName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                fastDFSUploadResult.setOriginalFileName(fileName);
                fastDFSUploadResult.setFileSuffix(extName);
                try {
                    //上传文件
                    String uploadResult = storageClient.upload_file1(fileContent, extName, null);
                    String fileHttpAccessUrl = fastDFSConfig.getHttpServer()+"/" + uploadResult;
                    long endTime = System.currentTimeMillis();
                    fastDFSUploadResult.setUploadSuccess(true);
                    String newFileName = uploadResult.substring(uploadResult.lastIndexOf("/")+1);
                    fastDFSUploadResult.setNewFileName(newFileName);
                    fastDFSUploadResult.setFileId(uploadResult);
                    fastDFSUploadResult.setFileUrl(fileHttpAccessUrl);
                    fastDFSUploadResult.setUseTime(endTime - startTime);
                    String fileMd5 = MD5.create().digestHex(fileContent);
                    fastDFSUploadResult.setFileMd5(fileMd5);//文件MD5
                    fastDFSUploadResult.setFileLength((long) fileContent.length);
                } catch (IOException e) {
                    fastDFSUploadResult.setUploadSuccess(false);
                    logger.error("Non IO Exception when uploading the file: ", e);
                } catch (Exception e) {
                    fastDFSUploadResult.setUploadSuccess(false);
                    logger.error("[上传文件（upload)][" + fileName + "][异常：" + e + "]");
                }
                uploadResultList.add(fastDFSUploadResult);
            }
        }  catch (Exception e) {
            logger.error("[上传文件（upload)][" + logId + "][异常：" + e + "]");
            throw ERRORS.SYS_ERROR.ERROR();
        }
        return uploadResultList;
    }

    /**
     * 删除文件
     * @param groupName 组名 如：group1
     * @param storagePath 不带组名的路径名称 如：M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     */
    public void deleteFile(String groupName, String storagePath)
            throws FastDFSException {
        deleteFile(groupName + "/" + storagePath);
    }

    /**
     *
     * @Description: 删除fastdfs服务器中文件
     * @param fileId 带组名的路径名称 如：group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @throws FastDFSException
     *
     */
    public void deleteFile(String fileId)
            throws FastDFSException {
        String logId = UUID.randomUUID().toString();
        TrackerServer trackerServer;
        try {
            TrackerClient trackerClient = new TrackerClient();
            /** 获取fastdfs服务器连接 */
            trackerServer = trackerClient.getTrackerServer();
            StorageServer storageServer = null;
            StorageClient1 client1 = new StorageClient1(trackerServer, storageServer);
            /** 删除文件,并释放 trackerServer */
            int result = client1.delete_file1(fileId);
            /** 0:文件删除成功，2：文件不存在 ，其它：文件删除出错 */
            if (result == 2) {
                throw ERRORS.NOT_EXIST_FILE.ERROR();
            } else if (result != 0) {
                throw ERRORS.DELETE_RESULT_ERROR.ERROR();
            }
        } catch (FastDFSException e) {
            logger.error("[ 删除文件（deleteFile）][" + logId + "][异常：" + e + "]");
            throw e;
        } catch (SocketTimeoutException e) {
            logger.error("[ 删除文件（deleteFile）][" + logId + "][异常：" + e + "]");
            throw ERRORS.WAIT_IDLECONNECTION_TIMEOUT.ERROR();
        } catch (Exception e) {
            logger.error("[ 删除文件（deleteFile）][" + logId + "][异常：" + e + "]");
            throw ERRORS.SYS_ERROR.ERROR();
        }
    }

    /**
     * 批量删除文件
     * @param group 组名 如：group1
     * @param storagePathList 不带组名的路径名称集合 如：M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return -1失败,0成功
     */
    public Map<String,Integer> batchDeleteFile(String group ,List<String> storagePathList) {
        Map<String, Integer> resultMap = new HashMap<>();
        int result = -1;
        for (String storagePath : storagePathList) {
            try {
                deleteFile(group, storagePath);
                result = 0;
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
            resultMap.put(storagePath, result);
        }
        return resultMap;
    }

    /**
     * 批量删除文件
     * @param fileIdList 带组名的路径名称集合 如：group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return -1失败,0成功
     */
    public Map<String,Integer> batchDeleteFile(List<String> fileIdList){
        Map<String,Integer> resultMap = new HashMap<>();
        int result=-1;
        for(String fileId:fileIdList){
            try {
               deleteFile(fileId);
                result = 0;
            }  catch (Exception e) {
               logger.error(e.getMessage(),e);
            }
            resultMap.put(fileId,result);
        }
        return  resultMap;
    }

    /**
     * 下载文件
     * @param group 组名 如：group1
     * @param storagePath 不带组名的路径名称 如：M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return 文件数组
     */
    public byte[] downloadFile(String group ,String storagePath) throws FastDFSException {
        return downloadFile(group + "/" + storagePath);
    }

    /**
     * 下载文件
     * @param fileId 带组名的路径名称 如：group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return 文件数组
     */
    public byte[] downloadFile(String fileId) throws FastDFSException {
        byte[] b = null;
        String logId = UUID.randomUUID().toString();
        TrackerServer trackerServer;
        try {
            TrackerClient trackerClient = new TrackerClient();
            /** 获取fastdfs服务器连接 */
            trackerServer = trackerClient.getTrackerServer();
            StorageServer storageServer = null;
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            b = storageClient.download_file1(fileId);
        } catch (MyException e) {
            e.printStackTrace();
        }catch (Exception e) {
            logger.error("[ 下载文件（downloadFile）][" + logId + "][异常：" + e + "]");
            throw ERRORS.SYS_ERROR.ERROR();
        }
        return b;
    }

    /**
     * 获取文件的Meta信息
     * @param fileId 带组名的路径名称 如：group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return FileInfo
     */
    public NameValuePair[] getFileMetaData(String fileId) throws FastDFSException {
        String logId = UUID.randomUUID().toString();
        TrackerServer trackerServer;
        try {
            TrackerClient trackerClient = new TrackerClient();
            /** 获取fastdfs服务器连接 */
            trackerServer = trackerClient.getTrackerServer();
            StorageServer storageServer = null;
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            NameValuePair[] nameValuePairs = storageClient.get_metadata1(fileId);
            return nameValuePairs;
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("[ 获取文件的Meta信息（getFileMetaData）][" + logId + "][异常：" + e + "]");
            throw ERRORS.SYS_ERROR.ERROR();
        }
        return null;
    }
}
