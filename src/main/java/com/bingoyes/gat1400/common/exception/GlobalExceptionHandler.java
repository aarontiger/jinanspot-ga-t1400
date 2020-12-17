package com.bingoyes.gat1400.common.exception;

import com.bingoyes.gat1400.apicaller.service.HzApiCallingService;
import com.bingoyes.gat1400.common.result.Result;
import com.bingoyes.gat1400.common.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public Result handleIllegalArgumentException(IllegalArgumentException e) {
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public Result handleException(ServiceException e) {
        log.error(">> 运行时异常:{}", e.getMessage());
        return Result.error(e.getResultCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result handleException(RuntimeException e) {
        log.error(">> 运行时异常:{}", e.getMessage());
        return Result.error(ResultCodeEnum.SYSTEM_EXECUTION_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(">> 系统异常，请联系系统管理员,{}", e.getMessage());
        return  Result.error(ResultCodeEnum.SYSTEM_EXECUTION_ERROR);
    }
}
