package com.thfdcsoft.framework.business.exception;

/**
 * @author ww
 * @date 2019/5/11 14:15
 */
public class PrintException extends RuntimeException{

    private boolean code;

    public PrintException(boolean code, String message) {
        super(message);
        this.code = code;
    }

    public boolean getCode() {
        return code;
    }
}
