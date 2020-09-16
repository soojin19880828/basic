package com.thfdcsoft.gov.dto.djzm;


import java.util.List;
/**
 　　* @description: 登记证明显示列表页面数据封装
 　　* @author 高拓
 　　* @date 2020/9/10 11:03
 　　*/
public class QueryEstateRspn {

    private String resultcode;
    private String resultmsg;
    private List<DjzmEstateResult> estateResults;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getResultmsg() {
        return resultmsg;
    }

    public void setResultmsg(String resultmsg) {
        this.resultmsg = resultmsg;
    }

    public List<DjzmEstateResult> getEstateResults() {
        return estateResults;
    }

    public void setEstateResults(List<DjzmEstateResult> estateResults) {
        this.estateResults = estateResults;
    }

    @Override
    public String toString() {
        return "QueryEstateRspn{" +
                "resultcode='" + resultcode + '\'' +
                ", resultmsg='" + resultmsg + '\'' +
                ", estateResults=" + estateResults +
                '}';
    }
}
