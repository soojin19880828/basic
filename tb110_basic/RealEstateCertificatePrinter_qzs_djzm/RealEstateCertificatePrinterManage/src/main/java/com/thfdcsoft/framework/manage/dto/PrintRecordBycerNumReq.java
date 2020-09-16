package com.thfdcsoft.framework.manage.dto;

import java.util.List;

/**
 * @Author: Tyrell
 * @Date: 2020-01-07 上午 10:12
 */
public class PrintRecordBycerNumReq {
    public List<String> cerNumList;

    public List<String> getCerNumsList() {
        return cerNumList;
    }

    public void setCerNumsList(List<String> cerNumsList) {
        this.cerNumList = cerNumsList;
    }

    public PrintRecordBycerNumReq() {
    }

    public PrintRecordBycerNumReq(List<String> cerNumList) {
        this.cerNumList = cerNumList;
    }

    @Override
    public String toString() {
        return "PrintRecordBycerNumReq{" +
                "cerNumList=" + cerNumList +
                '}';
    }
}
