package com.tenyon.common.domain.vo.resp;

import com.tenyon.common.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "通用响应类")
public class RtnData<T> implements Serializable {

    @Schema(description = "响应代码")
    private int code;

    @Schema(description = "响应内容")
    private T data;

    @Schema(description = "响应消息")
    private String message;

    public RtnData(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public RtnData(int code, T data) {
        this(code, data, "");
    }

    public RtnData(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }

    /**
     * 成功
     *
     * @param data 成功返回数据
     * @return 通用响应体
     */
    public static <T> RtnData<T> success(T data) {
        return new RtnData<>(0, data, "OK");
    }

    /**
     * 失败
     *
     * @return 通用响应体
     * @see ErrorCode
     */
    public static <T> RtnData<T> fail(ErrorCode errorCode) {
        return new RtnData<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code    错误代码
     * @param message 失败信息
     * @return 通用响应体
     */
    public static <T> RtnData<T> fail(int code, String message) {
        return new RtnData<>(code, null, message);
    }

    /**
     * 失败
     *
     * @param message 失败信息
     * @return 通用响应体
     * @see ErrorCode
     */
    public static <T> RtnData<T> fail(ErrorCode errorCode, String message) {
        return new RtnData<>(errorCode.getCode(), null, message);
    }
}
