package com.thfdcsoft.gov.dto.qzs;

import java.util.List;

/**
 * @author 高拓
 * @description: 系统商返回数据封装（根据实际情况，自行设置属性）
 * @date 2020/9/916:57
 */
public class QzsQueryRsp {
    private List<QzsQueryResultDto> data;

    private int result;

    private String msg;

    public List<QzsQueryResultDto> getData() {
        return data;
    }

    public void setData(List<QzsQueryResultDto> data) {
        this.data = data;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "QueryRsp{" +
                "data=" + data +
                ", result=" + result +
                ", msg='" + msg + '\'' +
                '}';
    }
}
