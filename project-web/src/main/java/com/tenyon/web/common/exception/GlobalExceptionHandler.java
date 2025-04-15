package com.tenyon.web.common.exception;

import com.tenyon.web.common.domain.vo.resp.RtnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public RtnData<?> businessExceptionHandler(BusinessException e) {
        logger.error("BusinessException", e);
        return RtnData.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public RtnData<?> runtimeExceptionHandler(RuntimeException e) {
        logger.error("RuntimeException", e);
        return RtnData.fail(ErrorCode.SYSTEM_ERROR, "系统错误");
    }

    @ExceptionHandler(Exception.class)
    public RtnData<?> handleException(Exception ex) {
        if (ex instanceof BindException) {
            String errorMsg = ((BindException) ex).getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).sorted().collect(Collectors.joining(","));
            return RtnData.fail(ErrorCode.SYSTEM_ERROR, errorMsg);
        }
        return RtnData.fail(ErrorCode.SYSTEM_ERROR);
    }
}
