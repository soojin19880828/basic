package com.thfdcsoft.framework.business.util;

import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;

/**
 * @author ww
 * @date 2019/5/11 15:32
 */
public class BaseHttpRspnUtil {

    public static BaseHttpRspn success(Object object) {
        BaseHttpRspn basebHttpRspn = new BaseHttpRspn();
        basebHttpRspn.setRespObj(object);
        basebHttpRspn.setResult(true);
        return basebHttpRspn;
    }

    public static BaseHttpRspn success() {
        return success(null);
    }

    public static BaseHttpRspn error(Boolean code, String msg) {
        BaseHttpRspn basebHttpRspn = new BaseHttpRspn();
        basebHttpRspn.setResult(code);
        basebHttpRspn.setRespMsg(msg);
        return basebHttpRspn;
    }
}
