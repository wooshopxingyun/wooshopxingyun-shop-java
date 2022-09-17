package com.wooshop.modules.tools.storage.fastdfs.exception;

//import com.wooshop.storage.fastdfs.exception.FastDFSException;

/**
 *
 * @ClassName: ERRORS
 * @Description: 应用异常枚举类
 * @author 孤傲苍狼 290603672@qq.com
 * @date 2017年10月22日 下午22:31:29
 *
 */
public enum ERRORS {

    FASTDFS_INIT_FAIL("21000", "连接fastdfs服务器失败", "连接fastdfs服务器失败，请检查配置文件的配置是否正确"),

    PARAMETER_IS_NULL("21001", "必填参数为空", "必填参数为空"),

    FASTDFS_CONNECTION_FAIL("21002", "连接fastdfs服务器失败", "文件上传异常，请重试"),

    WAIT_IDLECONNECTION_TIMEOUT("21003", "等待空闲连接超时", "连接超时，请重试"),

    NOT_EXIST_GROUP("21004", "文件组不存在", "文件组不存在"),

    UPLOAD_RESULT_ERROR("21005", "fastdfs文件系统上传返回结果错误", "文件上传异常，请重试"),

    NOT_EXIST_PORTURL("21006", "未找到对应的端口和访问地址", "文件上传异常，请重试"),

    SYS_ERROR("21007", "系统错误", "系统错误"),

    FILE_PATH_ERROR("21008", "文件访问地址格式不对","文件访问地址格式不对"),

    DELETE_RESULT_ERROR("21009", "fastdfs文件系统删除文件返回结果错误", "文件删除异常，请重试"),

    NOT_EXIST_FILE("21010", "文件不存在", "文件不存在");

    /** 错误码 */
    String code;

    /** 错误信息，用于日志输出，便于问题定位 */
    String message;

    /** 错误提示，用于客户端提示 */
    String descreption;

    ERRORS(String code, String message) {
        this.message = message;
        this.code = code;
    }

    ERRORS(String code, String message, String descreption) {
        this.message = message;
        this.code = code;
        this.descreption = descreption;
    }

    public FastDFSException ERROR() {
        return new FastDFSException(this.code, this.message,
                this.descreption);
    }

    public FastDFSException ERROR(String descreption) {
        return new FastDFSException(this.code, this.message,
                descreption);
    }
}
