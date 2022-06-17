package com.liumulin.common.model.response;

import lombok.Data;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

/**
 * 返回对象包装类(带泛型)
 *
 * @author Daniel Liu
 */
@Data
public class CommonResult<T> implements Serializable{

    //    public static final int NO_LOGIN = -1;
//    public static final int SUCCESS = 0;
//    public static final int CHECK_FAIL = 1;
//    public static final int NO_PERMISSION = 2;
//    public static final int UNKNOWN_EXCEPTION = -99;
//    private static final long serialVersionUID = 1L;

    private int code;

    /**
     * 返回的信息(主要出错的时候使用)
     */
    private String msg;

    /**
     * 返回的数据
     */
    private T data;

    public CommonResult() {
        super();
    }

    public CommonResult(T data) {
        super();
        this.data = data;
    }

    public static <T> CommonResult<T> success(T data) {
//        return new CommonResult<>(data);
        CommonResult<T> result = new CommonResult<>();
        result.code = CommonResultCode.SUCCESS.getCode();
        result.data = data;
        result.msg = "success";
        return result;
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        Assert.isTrue(!Objects.equals(CommonResultCode.SUCCESS.getCode(), code), "code 必须是错误码！");
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }

    /**
     * 将传入的 result 对象，转换成另外一个泛型结果的对象
     * <p>
     * 因为 A 方法返回的 CommonResult 对象，不满足调用其的 B 方法的返回，所以需要进行转换。
     *
     * @param result 传入的 result 对象
     * @param <T>    返回的泛型
     * @return 新的 CommonResult 对象
     */
    public static <T> CommonResult<T> error(CommonResult<?> result) {
        return error(result.getCode(), result.getMsg());
    }

    public static <T> CommonResult<T> error(ResultCode code) {
        return error(code.getCode(), code.getMsg());
    }
}
