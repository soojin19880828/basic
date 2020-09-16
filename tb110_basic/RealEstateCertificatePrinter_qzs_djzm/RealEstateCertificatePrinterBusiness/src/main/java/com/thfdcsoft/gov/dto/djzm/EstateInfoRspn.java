package com.thfdcsoft.gov.dto.djzm;

import java.util.List;
/**
 　　* @description: 登记证明查询数据封装
 　　* @author 高拓
 　　* @date 2020/9/10 10:57
 　　*/
public class EstateInfoRspn {
    private String resultcode;
    private String resultmsg;
    private List<DjzmQueryResultDto> queryResultDtoList;

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

    public List<DjzmQueryResultDto> getQueryResultDtoList() {
        return queryResultDtoList;
    }

    public void setQueryResultDtoList(List<DjzmQueryResultDto> queryResultDtoList) {
        this.queryResultDtoList = queryResultDtoList;
    }

    @Override
    public String toString() {
        return "QueryEstateRspn{" +
                "resultcode='" + resultcode + '\'' +
                ", resultmsg='" + resultmsg + '\'' +
                ", queryResultDtoList=" + queryResultDtoList +
                '}';
    }
}
