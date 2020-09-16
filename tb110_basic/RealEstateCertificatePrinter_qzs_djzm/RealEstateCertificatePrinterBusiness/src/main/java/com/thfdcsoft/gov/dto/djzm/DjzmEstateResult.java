package com.thfdcsoft.gov.dto.djzm;

/**
 * @author 高拓
 * @description: 登记证明查询结果返回封装
 * @date 2020/9/8 11:54
 */
public class DjzmEstateResult {
    private String location;
    private DjzmEstateInfo estateInfo;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DjzmEstateInfo getEstateInfo() {
        return estateInfo;
    }

    public void setEstateInfo(DjzmEstateInfo estateInfo) {
        this.estateInfo = estateInfo;
    }

    public DjzmEstateResult() {
    }

    public DjzmEstateResult(String location, DjzmEstateInfo estateInfo) {
        this.location = location;
        this.estateInfo = estateInfo;
    }
}
