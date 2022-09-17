package com.wooshop.modules.tools.storage.sftp.client;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.MD5;
import com.jcraft.jsch.*;
import com.wooshop.modules.tools.storage.sftp.bean.JschUploadResult;
import com.wooshop.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Created by gacl on 2017/10/16.
 */
public class SSH2Client {

    private static final Logger logger = LoggerFactory.getLogger(SSH2Client.class);

    /**
     * 文件上传后的存储方式
     */
    private static final String STORAG_TYPE = "SFTP";

    /**
     * 服务器配置
     */
    private SSH2Config config;

    public SSH2Client(SSH2Config SSH2Config){
        this.config = SSH2Config;
    }

    /**
     * ThreadLocal是解决线程安全问题一个很好的思路，它通过为每个线程提供一个独立的变量副本解决了变量并发访问的冲突问题。
     * 在很多情况下，ThreadLocal比直接使用synchronized同步机制解决线程安全问题更简单，更方便，且结果程序拥有更高的并发性。
     */
    //使用ThreadLocal解决线程安全问题
    private static ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<ChannelSftp> channelSftpThreadLocal = new ThreadLocal<>();

    /**
     * 在多线程环境中，单例模式会造成session和channelSftp（在类中属于全局变量）资源竞争，并且对属性有写操作，就可能会出现数据同步问题。
     * 线程安全问题都是由全局变量及静态变量引起的。
     * 多线程操作下会导致session和channelSftp有线程安全问题
     */
    //private static Session session = null;

    //private static ChannelSftp channelSftp = new ChannelSftp();

    private boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 连接SFTP服务器
     * @return
     */
    private boolean connectSFTP() {
        try {
            logger.debug("===============Creating Jsch session（创建一个Jsch Session）===============");
            JSch jsch = new JSch();
            Session session = jsch.getSession(config.getUserName(), config.getIp(), config.getPort());
            session.setPassword(config.getPassword());
            Properties configProperty = new Properties();
            configProperty.put("StrictHostKeyChecking", config.getStrictHostKeyChecking());
            /**
             * 使用这个配置项处理Jsch性能问题
             * configProperty.put("PreferredAuthentications","password");
             * 在使用中发现在session.connect时连接非常耗时，很慢
             * 经研究调试发现是开启gssapi认证导致，所以解决办法是关闭gssapi认证，只使用密码认证，
             */
            configProperty.put("PreferredAuthentications","password");//关闭gssapi认证，只使用密码认证
            session.setConfig(configProperty);
            logger.debug("Starting Jsch session");
            //session.connect如果不关闭gssapi认证时连接会非常耗时，要10秒钟左右，很慢
            long startTime = System.currentTimeMillis();
            session.connect();
            logger.debug("session.connect()连接耗时："+(System.currentTimeMillis()-startTime));
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            logger.debug("Openning sftp channel...");
            channelSftp.connect();
            sessionThreadLocal.set(session);
            channelSftpThreadLocal.set(channelSftp);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            disconnectSFTP();
            return false;
        }
        return true;
    }

    /**
     * 断开与SFTP服务器的连接
     */
    private void disconnectSFTP() {
        ChannelSftp channelSftp = channelSftpThreadLocal.get();
        if (channelSftp != null && channelSftp.isConnected()) {
            logger.debug("Closing sftp channel...");
            channelSftp.disconnect();
            channelSftpThreadLocal.remove();
        }
        Session session = sessionThreadLocal.get();
        if (session != null && session.isConnected()) {
            logger.debug("Disconnecting Jsch session");
            session.disconnect();
            sessionThreadLocal.remove();
        }
        logger.debug("===============Disconnecting Jsch session===============");
    }


    /**
     * 创建目录
     * @param createPath
     */
    private void createDir(String createPath) {
        ChannelSftp channelSftp = channelSftpThreadLocal.get();
        try {
            if (isDirExist(createPath)) {
                logger.debug(createPath + "目录已经存在，不需要创建");
                channelSftp.cd(createPath);
            }
            String pathArry[] = createPath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if (StringUtils.isEmpty(path)) {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString())) {
                    channelSftp.cd(filePath.toString());
                } else {
                    channelSftp.mkdir(filePath.toString());
                    logger.debug(createPath + "目录不存在，需要创建" + filePath.toString() + "目录");
                    channelSftp.cd(filePath.toString());
                }
            }
            channelSftp.cd(createPath);
        } catch (SftpException e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException("创建路径失败：" + createPath);
        }
    }

    /**
     * 判断文件夹是否存在.
     * @param dir 文件夹路径， /xxx/xxx/
     * @return 是否存在
     */
    public boolean dirExist(final String dir) {
        ChannelSftp channelSftp = channelSftpThreadLocal.get();
        try {
            Vector<?> vector = channelSftp.ls(dir);
            if (null == vector) {
                return false;
            } else {
                return true;
            }
        } catch (SftpException e) {
            return false;
        }
    }

    /**
     * 判断目录是否存在
     * @param directory
     * @return
     */
    private boolean isDirExist(String directory) {
        ChannelSftp channelSftp = channelSftpThreadLocal.get();
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = channelSftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if ("no such file".equals(e.getMessage().toLowerCase())) {
                isDirExistFlag = false;
            }
            //logger.error(e.getMessage(),e);
        }
        return isDirExistFlag;
    }

    /**
     * 上传文件
     * @param src 原路径
     * @param dst 目标存储路径
     * @throws SftpException 通过抛出异常来通知调用方上传失败
     */
    public void uploadFile(String src,String dst) throws SftpException {
        ChannelSftp channelSftp = channelSftpThreadLocal.get();
        //连接服务器
        boolean isConn = connectSFTP();
        if(!isConn){
            throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
        }
        //创建目录
        createDir(dst);
        channelSftp.put(src, dst, ChannelSftp.OVERWRITE);
        //断开与FTP服务器的连接
        disconnectSFTP();
    }

    /**
     * 上传文件并监听文件上传进度
     * @param src 原路径
     * @param dst 目标存储路径
     * @param progressMonitor 上传进度监控
     * @throws SftpException 通过抛出异常来通知调用方上传失败
     */
    public void uploadFile(String src,String dst,SftpProgressMonitor progressMonitor) throws SftpException {
        //连接服务器
        boolean isConn = connectSFTP();
        if(!isConn){
            throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
        }
        ChannelSftp channelSftp = channelSftpThreadLocal.get();
        //创建目录
        createDir(dst);
        channelSftp.put(src, dst, progressMonitor, ChannelSftp.OVERWRITE);
        //断开与FTP服务器的连接
        disconnectSFTP();
    }

    /**
     * 上传文件并监听文件上传进度
     * @param src input stream对象
     * @param dst 目标文件名
     * @param progressMonitor 监听文件上传进度
     * @throws SftpException 通过抛出异常来通知调用方上传失败
     */
    public void uploadFile(InputStream src,String dst,SftpProgressMonitor progressMonitor) throws SftpException {
        //连接服务器
        boolean isConn = connectSFTP();
        if(!isConn){
            throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
        }
        //创建目录
        createDir(dst);
        ChannelSftp channelSftp = channelSftpThreadLocal.get();
        /**
         * 将本地的input stream对象src上传到目标服务器，目标文件名为dst，dst不能为目录。
         指定文件传输模式为mode
         并使用实现了SftpProgressMonitor接口的monitor对象来监控传输的进度。
         */
        channelSftp.put(src, dst, progressMonitor, ChannelSftp.OVERWRITE);
        //断开与FTP服务器的连接
        disconnectSFTP();
    }

    /**
     * 上传文件并监听文件上传进度
     * @param src input stream对象
     * @param fileName 目标文件名
     * @param progressMonitor 监听文件上传进度
     * @throws SftpException 通过抛出异常来通知调用方上传失败
     */
    public void uploadFile(InputStream src,String fileName,String remotePath,SftpProgressMonitor progressMonitor) throws SftpException {
        //连接服务器
        boolean isConn = connectSFTP();
        if(!isConn){
            throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
        }
        ChannelSftp channelSftp = channelSftpThreadLocal.get();
        //创建保存文件的目录
        createDir(remotePath);
        //进入远程服务器目录
        channelSftp.cd(remotePath);
        /**
         * 将本地的input stream对象src上传到目标服务器，目标文件名为fileName，fileName不能为目录。
         指定文件传输模式为mode
         并使用实现了SftpProgressMonitor接口的monitor对象来监控传输的进度。
         */
        channelSftp.put(src,fileName,progressMonitor, ChannelSftp.OVERWRITE);
        //断开与FTP服务器的连接
        disconnectSFTP();
    }

    /**
     * 批量上传文件并监听文件上传进度
     * @param inputStreamMap input stream对象Map集合，key为文件名
     * @param remoteSavePath 文件上传后保存路径
     * @throws SftpException 通过抛出异常来通知调用方上传失败
     */
    public List<JschUploadResult> uploadFiles(Map<String,InputStream> inputStreamMap, String remoteSavePath) throws SftpException {
        return uploadFiles(inputStreamMap,remoteSavePath,null);
    }

    /**
     * 批量上传文件并监听文件上传进度
     * @param inputStreamMap input stream对象Map集合，key为文件名
     * @param remoteSavePath 文件上传后保存路径
     * @param progressMonitor 监听文件上传进度
     * @throws SftpException 通过抛出异常来通知调用方上传失败
     */
    public List<JschUploadResult> uploadFiles(Map<String,InputStream> inputStreamMap, String remoteSavePath, SftpProgressMonitor progressMonitor) throws SftpException {
        Long connectStartTime = System.currentTimeMillis();
        //连接服务器
        boolean isConn = connectSFTP();
        if(!isConn){
            throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
        }
        Long connectEndTime = System.currentTimeMillis();
        //连接SFTP服务器耗时
        Long connectTime = connectEndTime-connectStartTime;
        String remotePath;//存放目录
        if (isEmpty(remoteSavePath)) {
            remotePath = config.getWorkPath();
        } else {
            if (remoteSavePath.startsWith("/")) {
                remotePath = config.getWorkPath() + remoteSavePath;
            } else {
                remotePath = config.getWorkPath() + "/" + remoteSavePath;
            }
        }
        ChannelSftp channelSftp = channelSftpThreadLocal.get();
        //创建保存文件的目录
        createDir(remotePath);
        //进入远程服务器目录
        channelSftp.cd(remotePath);
        //上传结果集合
        List<JschUploadResult> jschUploadResults = new ArrayList<>();
        int count = 0;
        for(Map.Entry<String,InputStream> src:inputStreamMap.entrySet()){
            InputStream inputStream = src.getValue();
            String fileName = src.getKey();
            JschUploadResult jschUploadResult = new JschUploadResult();
            /**
             * 将本地的input stream对象上传到目标服务器，目标文件名为fileName，fileName不能为目录。
             指定文件传输模式为mode
             并使用实现了SftpProgressMonitor接口的monitor对象来监控传输的进度。
             */
            Long startTime = System.currentTimeMillis();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();// 后缀名
            jschUploadResult.setOriginalFileName(fileName);
            jschUploadResult.setFileSuffix(ext);
            String newFileName = IdUtil.randomUUID()+"."+ext;
            jschUploadResult.setNewFileName(newFileName);
            try {
                byte[] bytes = IoUtil.readBytes(inputStream);//inputStream只能读取一次
                jschUploadResult.setFileMd5(MD5.create().digestHex(bytes));//计算MD5
                jschUploadResult.setFileLength((long) bytes.length);
                channelSftp.put(new ByteArrayInputStream(bytes),newFileName,progressMonitor, ChannelSftp.OVERWRITE);
                jschUploadResult.setUploadSuccess(true);
                jschUploadResult.setSavePath(remotePath+"/"+newFileName);
                if (!isEmpty(remoteSavePath)) {
                    if (remoteSavePath.startsWith("/")) {
                        jschUploadResult.setFileUrl(config.getDomain() + remoteSavePath + "/" + newFileName);
                    } else {
                        jschUploadResult.setFileUrl(config.getDomain() + "/" + remoteSavePath + "/" + newFileName);
                    }
                } else {
                    jschUploadResult.setFileUrl(config.getDomain() + "/" + newFileName);
                }
                Long endTime = System.currentTimeMillis();
                if(count == 0){
                    //第一张图片的上传时间要加上连接服务器的时间
                    jschUploadResult.setUseTime(endTime-startTime+connectTime);
                }else{
                    jschUploadResult.setUseTime(endTime-startTime);
                }
            }catch (Exception e){
                logger.error(fileName+"上传出错"+e.getMessage(),e);
                jschUploadResult.setUploadSuccess(false);
            }
            count++;
            jschUploadResults.add(jschUploadResult);

        }
        //断开与FTP服务器的连接
        disconnectSFTP();
        return jschUploadResults;
    }


    /**
     * 列出某个目录下的所有文件
     * @param directory
     * @return
     * @throws SftpException
     */
    public Vector<ChannelSftp.LsEntry> listFiles(String directory) throws SftpException {
        //连接服务器
        boolean isConn = connectSFTP();
        if(!isConn){
            throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
        }
        ChannelSftp channelSftp = channelSftpThreadLocal.get();
        return channelSftp.ls(directory);
    }


    /**
     * 删除文件
     * @param directory 文件所在目录
     * @param fileName 要删除的文件名
     * @return -1失败,0成功
     */
    public int delete(String directory, String fileName) {
        int result = 0;
        try {
            //连接服务器
            boolean isConn = connectSFTP();
            if(!isConn){
                throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
            }
            ChannelSftp channelSftp = channelSftpThreadLocal.get();
            channelSftp.cd(directory);//进入目录
            channelSftp.rm(fileName);//删除文件
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result = -1;
        }finally{
            disconnectSFTP();
        }
        return result;
    }

    /**
     * 根据保存路径删除文件
     * @param savePath 文件所在目录
     * eg：/mydata/app1/uploadFilesManager/gacl/test/C5C5E1A94ECF4CA72A2B3566E8215022.jpg
     * @return -1失败,0成功
     */
    public int delete(String savePath) {
        int result = 0;
        try {
            //连接服务器
            boolean isConn = connectSFTP();
            if(!isConn){
                throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
            }
            ChannelSftp channelSftp = channelSftpThreadLocal.get();
            channelSftp.rm(savePath);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result = -1;
        }finally{
            disconnectSFTP();
        }
        return result;
    }

    /**
     * 批量删除
     * @param directory 文件所在目录
     * @param lstFileName 要删除的文件名集合
     * @return 以要删除的文件名为key，删除结果(-1失败,0成功)为value的Map结果集
     */
    public Map<String,Integer> batchDelete(String directory, List<String> lstFileName){
        Map<String,Integer> resultMap = new HashMap<>();
        try {
            //连接服务器
            boolean isConn = connectSFTP();
            if(!isConn){
                throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
            }
            ChannelSftp channelSftp = channelSftpThreadLocal.get();
            channelSftp.cd(directory);
            for(String fileName:lstFileName){
                int result=0;
                try {
                    channelSftp.rm(fileName);
                } catch (SftpException e) {
                    logger.error(e.getMessage(),e);
                    result = -1;
                }
                resultMap.put(fileName, result);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }finally{
            disconnectSFTP();
        }
        return resultMap;
    }

    /**
     * 批量删除
     * @param lstFileSavePath 要删除的文件路径
     * @return 以要删除的文件名为key，删除结果(-1失败,0成功)为value的Map结果集
     */
    public Map<String,Integer> batchDelete(List<String> lstFileSavePath){
        Map<String,Integer> resultMap = new HashMap<>();
        try {
            //连接服务器
            boolean isConn = connectSFTP();
            if(!isConn){
                throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
            }
            ChannelSftp channelSftp = channelSftpThreadLocal.get();
            for(String fileName:lstFileSavePath){
                int result=0;
                try {
                    channelSftp.rm(fileName);
                } catch (SftpException e) {
                    logger.error(e.getMessage(),e);
                    result = -1;
                }
                resultMap.put(fileName, result);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }finally{
            disconnectSFTP();
        }
        return resultMap;
    }

    /**
     * 下载文件
     * @param directory 文件所在目录
     * @param downloadFile 需要下载的文件名
     * @param savePath 文件保存路径
     */
    public void download(String directory, String downloadFile, String savePath) {
        try {
            //连接服务器
            boolean isConn = connectSFTP();
            if(!isConn){
                throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
            }
            ChannelSftp channelSftp = channelSftpThreadLocal.get();
            channelSftp.cd(directory);
            File file = new File(savePath);
            channelSftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }finally{
            disconnectSFTP();
        }
    }

    /**
     * 下载文件-sftp协议.
     * @param downloadFile 下载的文件
     * @param saveFile 存在本地的路径
     * @return File文件
     */
    public File downloadAsFile(final String downloadFile, final String saveFile) {
        //连接服务器
        boolean isConn = connectSFTP();
        if(!isConn){
            throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
        }
        FileOutputStream os = null;
        File file = new File(saveFile);
        try {
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                file.createNewFile();
            }
            ChannelSftp channelSftp = channelSftpThreadLocal.get();
            os = new FileOutputStream(file);
            channelSftp.get(downloadFile, os);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            try {
                if(os!=null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            disconnectSFTP();
        }
        return file;
    }

    /**
     * 下载文件-sftp协议.
     * @param downloadFile 下载的文件
     * @return 文件 byte[]
     * @throws Exception 异常
     */
    public byte[] downloadAsByte(final String downloadFile){
        //连接服务器
        boolean isConn = connectSFTP();
        if(!isConn){
            throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ChannelSftp channelSftp = channelSftpThreadLocal.get();
            channelSftp.get(downloadFile, os);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            disconnectSFTP();
        }
        return os.toByteArray();
    }

    /**
     * 下载文件
     * @param directory 文件所在目录
     * @param downloadFile 需要下载的文件名
     * @return InputStream 文件输入流
     */
    public InputStream download(String directory, String downloadFile) {
        try {
            //连接服务器
            boolean isConn = connectSFTP();
            if(!isConn){
                throw new RuntimeException("连接服务器失败！请检查连接参数是否正确");
            }
            ChannelSftp channelSftp = channelSftpThreadLocal.get();
            channelSftp.cd(directory);
            return channelSftp.get(downloadFile);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }finally{
            disconnectSFTP();
        }
    }

    public SSH2Config getConfig() {
        return config;
    }

    public void setConfig(SSH2Config config) {
        this.config = config;
    }

    /**
     * 格式化路径.()
     * @param srcPath 原路径. /xxx/xxx/xxx.yyy 或 X:/xxx/xxx/xxx.yy
     *                /mydata/app1/uploadFilesManager/gacl/test/C5C5E1A94ECF4CA72A2B3566E8215022.jpg
     * @return list, 第一个是路径（/xxx/xxx/）,第二个是文件名（xxx.yy）
     */
    private List<String> formatPath(final String srcPath) {
        List<String> list = new ArrayList<>(2);
        String repSrc = srcPath.replaceAll("\\\\", "/");
        int firstP = repSrc.indexOf("/");
        int lastP = repSrc.lastIndexOf("/");
        String fileName = lastP + 1 == repSrc.length() ? "" : repSrc.substring(lastP + 1);
        String dir = firstP == -1 ? "" : repSrc.substring(firstP, lastP);
        dir = (dir.length() == 1 ? dir : (dir + "/"));
        list.add(dir);
        list.add(fileName);
        return list;
    }
}
