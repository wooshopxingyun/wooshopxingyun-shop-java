package com.wooshop.modules.tools.storage.fastdfs.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * FastDFS上传文件返回结果的JavaBean
 */
@Data
public class FastDFSUploadResult implements Serializable {

    /**
     * 上传文件的原始文件名
     */
    private String originalFileName;

    /**
     * 重命名后的文件名
     */
    private String newFileName;

    /**
     * 文件上传成功后生成的唯一标识
     */
    private String fileId;

    /**
     * 文件网络访问URL地址
     */
    private String fileUrl;

    /**
     * 文件后缀名
     */
    private String fileSuffix;

    /**
     * 上传结果：true:成功
     *           false：失败
     *
     */
    private boolean isUploadSuccess;


    /**
     * 上传使用时间
     */
    private Long useTime;

    /**
     * 上传文件的MD5
     */
    private String fileMd5;

    /**
     * 文件大小
     */
    private Long fileLength;
}
