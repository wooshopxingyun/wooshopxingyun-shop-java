package com.wooshop.modules.tools.storage.fastdfs;

import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.digest.MD5;
import com.wooshop.modules.tools.storage.fastdfs.bean.FastDFSFile;
import com.wooshop.modules.tools.storage.fastdfs.bean.FastDFSUploadResult;
import com.wooshop.modules.tools.storage.fastdfs.client.FastDFSClient;
import com.wooshop.modules.tools.storage.fastdfs.exception.FastDFSException;
import com.wooshop.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于Fastdfs上传实现
 * Created by gacl on 2017/10/16.
 */
public class FastdfsService {

    private static final Logger logger = LoggerFactory.getLogger(FastdfsService.class);

    /**
     * FastDFSClient
     */
    private FastDFSClient fastDFSClient;

    public void setFastDFSClient(FastDFSClient fastDFSClient){
        this.fastDFSClient = fastDFSClient;
    }


    public FastDFSUploadResult uploadFile(String filePath) {
        File file = new File(filePath);
        return uploadFile(file);
    }

    public FastDFSUploadResult uploadFile(InputStream inputStream, String fileName) {
        Long startTime = System.currentTimeMillis();
        FastDFSUploadResult uploadResult = new FastDFSUploadResult();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();// 后缀名
        uploadResult.setOriginalFileName(fileName);
        uploadResult.setFileSuffix(ext);
        try {
            byte[] content = new byte[0];
            try {
                content = IoUtil.readBytes(inputStream);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
            FastDFSFile fastDFSFile = new FastDFSFile(fileName, content, ext);
            //上传文件到fastDFS服务器
            fastDFSClient.uploadFile(fastDFSFile);
            uploadResult.setFileUrl(fastDFSFile.getRemotePath());
            String fileId = fastDFSFile.getGroup() + "/" + fastDFSFile.getStoragePath();
            uploadResult.setFileId(fileId);
            String newFileName = fileId.substring(fileId.lastIndexOf("/")+1);
            uploadResult.setNewFileName(newFileName);
            uploadResult.setUploadSuccess(true);
            String fileMd5 = MD5.create().digestHex(content);
            uploadResult.setFileMd5(fileMd5);//文件MD5
            uploadResult.setFileLength((long) content.length);
        } catch (Exception ex) {
            logger.error("将文件上传到FastDFS失败！", ex);
            uploadResult.setUploadSuccess(false);
        }
        Long endTime = System.currentTimeMillis();
        uploadResult.setUseTime(endTime-startTime);
        return uploadResult;
    }

    public FastDFSUploadResult uploadFile(File file) {
        String fileName = file.getName();
        Long startTime = System.currentTimeMillis();
        FastDFSUploadResult uploadResult = new FastDFSUploadResult();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();// 后缀名
        uploadResult.setOriginalFileName(fileName);
        uploadResult.setFileSuffix(ext);
        uploadResult.setFileLength(file.length());
        try {
            byte[] content = new byte[0];
            try {
                content = FileUtil.readBytes(file);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
            FastDFSFile fastDFSFile = new FastDFSFile(fileName, content, ext);
            //上传文件到fastDFS服务器
            fastDFSClient.uploadFile(fastDFSFile);
            uploadResult.setFileUrl(fastDFSFile.getRemotePath());
            String fileId = fastDFSFile.getGroup() + "/" + fastDFSFile.getStoragePath();
            uploadResult.setFileId(fileId);
            String newFileName = fileId.substring(fileId.lastIndexOf("/")+1);
            uploadResult.setNewFileName(newFileName);
            uploadResult.setUploadSuccess(true);
            String fileMd5 = MD5.create().digestHex(file);
            uploadResult.setFileMd5(fileMd5);//文件MD5
        } catch (Exception ex) {
            logger.error("将文件上传到FastDFS失败！", ex);
            uploadResult.setUploadSuccess(false);
        }
        Long endTime = System.currentTimeMillis();
        uploadResult.setUseTime(endTime-startTime);
        return uploadResult;
    }

    public FastDFSUploadResult uploadFile(byte[] fileByte, String fileName) {
        Long startTime = System.currentTimeMillis();
        FastDFSUploadResult uploadResult = new FastDFSUploadResult();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();// 后缀名
        uploadResult.setOriginalFileName(fileName);
        uploadResult.setFileSuffix(ext);
        try {
            FastDFSFile fastDFSFile = new FastDFSFile(fileName, fileByte, ext);
            //上传文件到fastDFS服务器
            fastDFSClient.uploadFile(fastDFSFile);
            uploadResult.setFileUrl(fastDFSFile.getRemotePath());
            String fileId = fastDFSFile.getGroup() + "/" + fastDFSFile.getStoragePath();
            uploadResult.setFileId(fileId);
            String newFileName = fileId.substring(fileId.lastIndexOf("/")+1);
            uploadResult.setNewFileName(newFileName);
            uploadResult.setUploadSuccess(true);
            String fileMd5 = MD5.create().digestHex16(fileByte);
            uploadResult.setFileMd5(fileMd5);//文件MD5
            uploadResult.setFileLength((long) fileByte.length);
        }catch (Exception e){
            logger.error("将文件上传到FastDFS失败！", e);
            uploadResult.setUploadSuccess(false);
        }
        Long endTime = System.currentTimeMillis();
        uploadResult.setUseTime(endTime-startTime);
        return uploadResult;
    }

    public List<FastDFSUploadResult> uploadFile(Map<String, InputStream> inputStreamMap){
        try {
            return fastDFSClient.uploadFile(inputStreamMap);
        } catch (FastDFSException e) {
            return null;
        }
    }

    public FastDFSUploadResult uploadNetworkFile(String fileUrl) throws MalformedURLException {
        return uploadNetworkFile(fileUrl,"");
    }

    public FastDFSUploadResult uploadNetworkFile(String fileUrl, String fileExt) throws MalformedURLException {
        //下载网络文件
        byte[] content = FileUtil.downloadNetworkFile(fileUrl);
        String ext;
        if(!isEmpty(fileExt)){
            ext = fileExt;
        }else{
            ext = fileUrl.substring(fileUrl.lastIndexOf(".") + 1).toLowerCase();// 后缀名
        }
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1).toLowerCase()+"."+ext;
        return uploadFile(content,fileName);
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件URL
     * @return -1：删除失败,0：删除成功
     */
    public int deleteFile(String fileUrl) {
        String fileId = fileUrl.substring(fastDFSClient.getFastDFSConfig().getHttpServer().length()+1);
        int result = 0;
        try {
            fastDFSClient.deleteFile(fileId);
        } catch (FastDFSException e) {
            result = -1;
        }
        return result;
    }

    /**
     * 批量删除文件
     *
     * @param listFileUrl 要删除的文件名集合
     * @return -1：删除失败,0：删除成功
     */
    public Map<String, Integer> deleteFile(List<String> listFileUrl) {
        List<String> fileIdList = new ArrayList<>();
        listFileUrl.forEach(fileUrl->{
            String fileId = fileUrl.substring(fastDFSClient.getFastDFSConfig().getHttpServer().length()+1);
            fileIdList.add(fileId);
        });
        return fastDFSClient.batchDeleteFile(fileIdList);
    }

    public int deleteFile(String directory, String fileName) {
        int result = 0;
        try {
            fastDFSClient.deleteFile(directory, fileName);
        } catch (FastDFSException e) {
            result = -1;
        }
        return result;
    }

    public Map<String,Integer> batchDeleteFile(String directory, List<String> lstFileName) {
        return fastDFSClient.batchDeleteFile(directory, lstFileName);
    }

    public byte[] downloadFile(String fileUrl) {
        try {
            //下载网络文件
            return  FileUtil.downloadNetworkFile(fileUrl);
        }catch (Exception e){
            return null;
        }
    }

    public byte[] downloadFile(String directory, String fileName) {
        try {
            return fastDFSClient.downloadFile(directory, fileName);
        } catch (FastDFSException e) {
            return null;
        }
    }

    public void downloadFile(String directory, String fileName, String savePath) {
        byte[] fileByte = downloadFile(directory, fileName);
        if(fileByte != null){
            try {
                FileUtil.writeBytes(fileByte,savePath);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }
    }

    private boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
