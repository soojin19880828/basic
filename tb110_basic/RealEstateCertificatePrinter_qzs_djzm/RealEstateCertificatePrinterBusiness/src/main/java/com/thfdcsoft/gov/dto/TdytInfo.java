package com.thfdcsoft.gov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 土地用途信息
 */
public class TdytInfo {
    /**用途名称*/
    @JsonProperty(value = "YTMC")
    private String ytmc;

    /**开始日期*/
    @JsonProperty(value = "KSRQ")
    private String ksrq;

    /**结束日期*/
    @JsonProperty(value = "JSRQ")
    private String jsrq;

    public String getYtmc() {
        return ytmc;
    }

    public void setYtmc(String ytmc) {
        this.ytmc = ytmc;
    }

    public String getKsrq() {
        return ksrq;
    }

    public void setKsrq(String ksrq) {
        this.ksrq = ksrq;
    }

    public String getJsrq() {
        return jsrq;
    }

    public void setJsrq(String jsrq) {
        this.jsrq = jsrq;
    }

    @Override
    public String toString() {
        return "TdytInfo{" +
                "ytmc='" + ytmc + '\'' +
                ", ksrq='" + ksrq + '\'' +
                ", jsrq='" + jsrq + '\'' +
                '}';
    }
}
