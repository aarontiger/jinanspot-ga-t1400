package com.bingoyes.gat1400.common.result;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @date 2020-06-23
 **/
@Data
public class Result<T> implements Serializable {

    private String code;

    private T data;

    private String msg;


    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        ResultCodeEnum rce = ResultCodeEnum.SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            rce = ResultCodeEnum.SYSTEM_EXECUTION_ERROR;
        }
        return result(rce, data);
    }


    public static <T> Result<T> error() {
        return result(ResultCodeEnum.SYSTEM_EXECUTION_ERROR.getCode(), ResultCodeEnum.SYSTEM_EXECUTION_ERROR.getMsg(), null);
    }

    public static <T> Result<T> error(String msg){
        return result(ResultCodeEnum.SYSTEM_EXECUTION_ERROR.getCode(), msg, null);
    }

    public static <T> Result<T> error(IResultCode resultCode) {
        return result(resultCode.getCode(), resultCode.getMsg(), null);
    }

    public static <T> Result<T> status(boolean status) {
        if (status) {
            return success();
        } else {
            return error();
        }
    }

    public static <T> Result<T> custom(IResultCode resultCode) {
        return result(resultCode.getCode(), resultCode.getMsg(), null);
    }


    private static <T> Result<T> result(IResultCode resultCode, T data) {
        return result(resultCode.getCode(), resultCode.getMsg(), data);
    }

    private static <T> Result<T> result(String code, String msg, T data) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
