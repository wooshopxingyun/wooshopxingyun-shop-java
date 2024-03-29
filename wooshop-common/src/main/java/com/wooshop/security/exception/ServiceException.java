package com.wooshop.security.exception;


import com.wooshop.security.enums.ResultCode;
import lombok.Data;

/**
 * 全局业务异常类
 *
 * @author woo
 * @version v4.0
 * @since 20202/06/14 16:20
 */
@Data
public class ServiceException extends RuntimeException {

    public static String DEFAULT_MESSAGE = "网络错误，请稍后重试！";

    /**
     * 异常消息
     */
    private String msg = DEFAULT_MESSAGE;

    /**
     * 错误码
     */
    private ResultCode resultCode;

    public ServiceException(String msg) {
        this.resultCode = ResultCode.ERROR;
        this.msg = msg;
    }

    public ServiceException() {
        super();
    }

    public ServiceException(ResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, String message) {
        this.resultCode = resultCode;
        this.msg = message;
    }

}
