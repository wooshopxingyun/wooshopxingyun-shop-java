package com.wooshop.modules.tools.storage.sftp;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.MD5;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
import com.wooshop.modules.tools.storage.sftp.bean.JschUploadResult;
import com.wooshop.modules.tools.storage.sftp.client.SSH2Client;
import com.wooshop.modules.tools.storage.sftp.client.SSH2Config;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于Jsch实现的Sftp上传实现
 */
public class JschService {

    private static Logger logger = LoggerFactory.getLogger(JschService.class);

    private SSH2Client ssh2Client;

    private SSH2Config ssh2Config;

    public JschService(SSH2Client ssh2Client){
        this.ssh2Client = ssh2Client;
        ssh2Config = this.ssh2Client.getConfig();
    }


    public JschUploadResult uploadFile(String filePath) {
        return uploadFile(filePath,"");
    }


    public JschUploadResult uploadFile(String filePath, String savePath) {
        File file = new File(filePath);
        return uploadFile(file,savePath);
    }


    public JschUploadResult uploadFile(InputStream inputStream, String fileName) {
        return upload(inputStream,fileName,"");
    }

    /**
     * 一次性上传多个文件
     * @param inputStreamMap 以原始文件名(带后缀,如1.png,2.jpg)为key的输入流Map
     * @return List<UploadResult> 上传结果集合
     */
    public List<JschUploadResult> uploadFile(Map<String,InputStream> inputStreamMap){
        return uploadFile(inputStreamMap, "");
    }

    /**
     * 一次性上传多个文件
     * @param inputStreamMap 以原始文件名(带后缀,如1.png,2.jpg)为key的输入流Map
     * @param savePath 文件上传后的保存目录
     * @return List<UploadResult> 上传结果集合
     */
    public List<JschUploadResult> uploadFile(Map<String,InputStream> inputStreamMap, String savePath){
        return uploadFiles2SFtp(inputStreamMap,savePath);
    }

    public JschUploadResult uploadFile(InputStream inputStream, String fileName, String savePath) {
        return upload(inputStream,fileName,savePath);
    }


    public JschUploadResult uploadFile(File file, String savePath) {
        return upload(file,savePath);
    }


    public JschUploadResult uploadFile(byte[] fileByte, String fileName) {
        return upload(fileByte,fileName,"");
    }


    public JschUploadResult uploadFile(byte[] fileByte, String fileName, String savePath) {
        return upload(fileByte,fileName,savePath);
    }

    public JschUploadResult uploadNetworkFile(String fileUrl) throws MalformedURLException {
        return uploadNetworkFile(fileUrl,"");
    }

    public JschUploadResult uploadNetworkFile(String fileUrl, String fileExt) throws MalformedURLException {
        //下载网络文件
        byte[] content = FileUtil.downloadNetworkFile(fileUrl);
        String ext;
        if(!StringUtils.isEmpty(fileExt)){
            ext = fileExt;
        }else{
            ext = fileUrl.substring(fileUrl.lastIndexOf(".") + 1).toLowerCase();// 后缀名
        }
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1).toLowerCase()+"."+ext;
        return uploadFile(content,fileName);
    }

    public int deleteFile(String fileUrl) {
        String savePath = ssh2Config.getWorkPath()+fileUrl.substring(ssh2Config.getDomain().length());
        return ssh2Client.delete(savePath);
    }

    public Map<String,Integer> deleteFile(List<String> fileUrlList) {
        List<String> lstFileSavePath = new ArrayList<>();
        fileUrlList.forEach(fileUrl->{
            String savePath = ssh2Config.getWorkPath()+fileUrl.substring(ssh2Config.getDomain().length());
            lstFileSavePath.add(savePath);
        });
        return batchDeleteFile(lstFileSavePath);
    }

    /**
     * 删除文件
     * @param directory 文件所在的目录
     *                  /mydata/app1/uploadFilesManager/gacl/test
     * @param fileName 文件名
     *                 4F2D9F19813CA90B22C6EC7EA769D2EB.jpg
     * @return -1失败,0成功
     */
    public int deleteFile(String directory, String fileName) {
        return ssh2Client.delete(directory,fileName);
    }


    /**
     * 批量删除
     * @param directory 文件所在目录
     * @param lstFileName 要删除的文件名集合
     * @return 以要删除的文件名为key，删除结果(-1失败,0成功)为value的Map结果集
     */
    public Map<String,Integer> batchDeleteFile(String directory, List<String> lstFileName) {
        return ssh2Client.batchDelete(directory, lstFileName);
    }

    /**
     * 批量删除
     * @param lstFileSavePath 要删除的文件路径
     * @return 以要删除的文件名为key，删除结果(-1失败,0成功)为value的Map结果集
     */
    public Map<String,Integer> batchDeleteFile(List<String> lstFileSavePath) {
        return ssh2Client.batchDelete(lstFileSavePath);
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
        InputStream inputStream = ssh2Client.download(directory, fileName);
        if(inputStream != null){
            try {
                return IoUtil.readBytes(inputStream);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }
        return null;
    }

    public byte[] downloadAsByte(String downloadFile) {
        return ssh2Client.downloadAsByte(downloadFile);
    }


    public void downloadFile(String directory, String fileName, String savePath) {
        ssh2Client.download(directory,fileName,savePath);
    }

    public File downloadAsFile(String downloadFile,String savePath) {
        return ssh2Client.downloadAsFile(downloadFile, savePath);
    }

    /**
     * 将单个文件上传到远程Linux服务器
     * @param inputStream 文件的输入流
     * @param fileName 文件名（带后缀）
     * @param remoteSavePath 远程保存路径
     * @return UploadResult 上传结果
     */
    private JschUploadResult upload(InputStream inputStream, String fileName, final String remoteSavePath){
        Long startTime = System.currentTimeMillis();
        JschUploadResult uploadResult = new JschUploadResult();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();// 后缀名
        uploadResult.setOriginalFileName(fileName);
        uploadResult.setFileSuffix(ext);
        String newFileName = IdUtil.randomUUID()+"."+ext;
        uploadResult.setNewFileName(newFileName);
        try {
            upload(inputStream, uploadResult, newFileName, remoteSavePath);
        } catch (Exception ex) {
            uploadResult.setUploadSuccess(false);//上传失败
            logger.error("将文件上传到SFTP服务器失败！", ex);
        }
        Long endTime = System.currentTimeMillis();
        uploadResult.setUseTime(endTime-startTime);
        return uploadResult;
    }

    /**
     * 将单个multipartFile文件上传到远程Linux服务器
     * @param file 需要上传的文件
     * @param remoteSavePath 文件存放目录
     * @return UploadResult 上传结果
     */
    private JschUploadResult upload(File file, final String remoteSavePath){
        Long startTime = System.currentTimeMillis();
        JschUploadResult uploadResult = new JschUploadResult();
        String fileName = file.getName();// 文件名
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();// 后缀名
        uploadResult.setOriginalFileName(fileName);
        uploadResult.setFileSuffix(ext);
        String newFileName = IdUtil.randomUUID()+"."+ext;
        uploadResult.setNewFileName(newFileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            upload(fileInputStream,uploadResult,newFileName,remoteSavePath);
        } catch (IOException ex) {
            uploadResult.setUploadSuccess(false);//上传失败
            logger.error("将文件上传到SFTP服务器失败！", ex);
        }
        Long endTime = System.currentTimeMillis();
        uploadResult.setUseTime(endTime-startTime);
        String fileMd5 = MD5.create().digestHex16(file);
        uploadResult.setFileMd5(fileMd5);
        uploadResult.setFileLength(file.length());
        return uploadResult;
    }

    /**
     * 将单个文件上传到远程Linux服务器
     * @param fileByte 文件的二进制数据
     * @param fileName 文件名（带后缀）
     * @param remoteSavePath 远程保存路径
     * @return UploadResult 上传结果
     */
    private JschUploadResult upload(byte[] fileByte, String fileName, final String remoteSavePath){
        Long startTime = System.currentTimeMillis();
        JschUploadResult uploadResult = new JschUploadResult();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();// 后缀名
        uploadResult.setOriginalFileName(fileName);
        uploadResult.setFileSuffix(ext);
        String newFileName = IdUtil.randomUUID()+"."+ext;
        uploadResult.setNewFileName(newFileName);
        try {
            InputStream in = new ByteArrayInputStream(fileByte);
            upload(in,uploadResult,newFileName,remoteSavePath);
        } catch (Exception ex) {
            uploadResult.setUploadSuccess(false);//上传失败
            logger.error("将文件上传到SFTP服务器失败！", ex);
        }
        Long endTime = System.currentTimeMillis();
        uploadResult.setUseTime(endTime-startTime);
        String fileMd5 = MD5.create().digestHex16(fileByte);
        uploadResult.setFileMd5(fileMd5);
        uploadResult.setFileLength((long) fileByte.length);
        return uploadResult;
    }

    /**
     * 将单个文件上传到远程Linux服务器
     * @param is 文件输入流
     * @param jschUploadResult 上传后的响应对象，里面封装了返回的文件上传信息
     * @param nFileName 新文件名
     * @param remoteSavePath 文件存放目录
     */
    private void upload(InputStream is, final JschUploadResult jschUploadResult, final String nFileName, final String remoteSavePath) {
        try {
            String remotePath;//存放目录
            if(StringUtils.isEmpty(remoteSavePath)){
                remotePath = ssh2Config.getWorkPath();
            }else{
                if(remoteSavePath.startsWith("/")){
                    remotePath = ssh2Config.getWorkPath()+remoteSavePath;
                }else{
                    remotePath = ssh2Config.getWorkPath()+"/"+remoteSavePath;
                }
            }
            /**
             * 将文件上传到服务器
             */
            ssh2Client.uploadFile(is, nFileName, remotePath, new SftpProgressMonitor() {
                private long transfered;

                @Override
                public void init(int i, String s, String s1, long l) {
                    jschUploadResult.setSavePath(s1);
                }

                @Override
                public boolean count(long l) {
                    transfered = transfered + l;
                    return true;
                }

                @Override
                public void end() {
                    if (!StringUtils.isEmpty(remoteSavePath)) {
                        if (remoteSavePath.startsWith("/")) {
                            jschUploadResult.setFileUrl(ssh2Config.getDomain() + remoteSavePath + "/" + nFileName);
                        } else {
                            jschUploadResult.setFileUrl(ssh2Config.getDomain() + "/" + remoteSavePath + "/" + nFileName);
                        }
                    } else {
                        jschUploadResult.setFileUrl(ssh2Config.getDomain() + "/" + nFileName);
                    }
                    jschUploadResult.setUploadSuccess(true);//上传成功
                }
            });
        } catch (Exception ex) {
            jschUploadResult.setUploadSuccess(false);//上传失败
            logger.error("将文件上传到服务器失败！", ex);
        }
    }

    /**
     * 将多个文件上传到远程Linux服务器
     * @param inputStreamMap 需要上传的文件输入流Map
     * @param remoteSavePath 保存路径
     * @return List<JschUploadResult> null则表示全部上传失败
     */
    private List<JschUploadResult> uploadFiles2SFtp(Map<String,InputStream> inputStreamMap, final String remoteSavePath) {
        try {
           return ssh2Client.uploadFiles(inputStreamMap,remoteSavePath);
        } catch (SftpException e) {
            logger.error("将文件上传到服务器失败！", e);
            return null;
        }
    }

}
