package com.wooshop.modules.tools.storage.fastdfs.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * FastDFS文件Bean
 * @author 孤傲苍狼 290603672@qq.com
 */
public class FastDFSFile implements Serializable {

    //文件名
    private String name;

    //文件的二进制流
    private byte[] content;

    //文件扩展名
    private String ext;

    /**
     * 上传后存放的远程路径
     */
    private String remotePath;

    /**
     * 不带组名的存储路径
     */
    private String storagePath;

    /**
     * 组名
     */
    private String group;

    /**
     * 文件上传成功后的存储路径：由group+storagePath组成
     */
    private String fileId;

    /**
     * 文件的meta信息
     */
    private Map<String,String> fileMetaMap = new HashMap<>();

    public FastDFSFile(String name, byte[] content, String ext) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;
    }

    public FastDFSFile() {

    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Map<String, String> getFileMetaMap() {
        return fileMetaMap;
    }

    public void setFileMetaMap(Map<String, String> fileMetaMap) {
        this.fileMetaMap = fileMetaMap;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "FastDFSFile{" +
                "name='" + name + '\'' +
                ", content=" + Arrays.toString(content) +
                ", ext='" + ext + '\'' +
                ", remotePath='" + remotePath + '\'' +
                ", storagePath='" + storagePath + '\'' +
                ", group='" + group + '\'' +
                ", fileId='" + fileId + '\'' +
                ", fileMetaMap=" + fileMetaMap +
                '}';
    }
}
