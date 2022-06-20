package com.liumulin.common.model.response;

/**
 * @author Daniel Liu
 */
public interface ResultCode {

    /**
     * 操作代码
     * @return code
     */
    int getCode();

    /**
     * 提示信息
     * @return 提示消息
     */
    String getMessage();

}