package com.thfdcsoft.framework.business.handler;

import com.thfdcsoft.framework.business.exception.PrintException;
import com.thfdcsoft.framework.business.util.BaseHttpRspnUtil;
import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ww
 * @date 2019/5/11 15:25
 */
@ControllerAdvice
public class PrintExceptionHandler {

    /**
     * 拦截异常
     * @return
     */
    @ExceptionHandler(value = PrintException.class)/*要拦截的异常*/
    @ResponseBody
    public BaseHttpRspn handlerSellException(PrintException e){
        return BaseHttpRspnUtil.error(e.getCode(), e.getMessage());
    }
}
