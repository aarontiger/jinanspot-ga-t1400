package com.bingoyes.gat1400.common.exception;

import com.bingoyes.gat1400.common.result.IResultCode;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    public IResultCode resultCode;

    public IResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(IResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ServiceException(IResultCode errorCode) {
        super(errorCode.getMsg());
        this.resultCode = errorCode;
    }

    public ServiceException(String message){
        super(message);
    }

    public ServiceException(String message, Throwable cause){
        super(message, cause);
    }

    public ServiceException(Throwable cause){
        super(cause);
    }
}
