package com.liumulin.common.exception;

import com.liumulin.common.model.response.ResultCode;
import lombok.Getter;

/**
 * 自定义异常，继承 RuntimeException 使得对代码无侵入行，若是继承 Exception，在 throw new Exception 时则需要 try/catch 或是抛出
 * <p>
 * 系统对异常处理使用统一的异常处理流程：
 * 1、自定义异常类型。
 * 2、自定义错误代码及错误信息。
 * 3、对于可预知的异常由程序员在代码中主动抛出，由 SpringMVC 统一捕获。可预知异常是程序员在代码中手动抛出本系统定义的特定异常类型，
 * 由于是程序员抛出的异常，通常异常信息比较齐全，程序员在抛出时会指定错误代码及错误信息，获取异常信息也比较方便。
 * 4、对于不可预知的异常（运行时异常）由 SpringMVC 统一捕获 Exception 类型的异常。
 * 不可预知异常通常是由于系统出现 bug、或一些不要抗拒的错误（比如网络中断、服务器宕机等），异常类型为 RuntimeException 类型（运行时异常）。
 * 5、可预知的异常及不可预知的运行时异常最终会采用统一的信息格式（错误代码 + 错误信息）来表示，最终也会随请求响应给客户端。
 * @author Daniel Liu
 */
@Getter
public class BusinessException extends RuntimeException {
    /**
     * 异常对应的错误类型
     */
    private final ResultCode resultCode;

    /**
     * 默认是系统异常
     */
//    public CustomException() {
//        this.resultCode = CommonResultCode.SERVER_ERROR;
//    }

    public BusinessException(ResultCode resultCode) {
        //异常信息为：错误代码+异常信息
        super("错误代码：" + resultCode.getCode() + "；异常信息：" + resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public BusinessException(String message) {
        super(message);
        this.resultCode = null;
    }

//    public CustomException(ResultCode resultCode, String message) {
//        super(message);
//        this.resultCode = resultCode;
//    }
//
//    public CustomException(ResultCode resultCode, String message, Throwable cause) {
//        super(message, cause);
//        this.resultCode = resultCode;
//    }
}
