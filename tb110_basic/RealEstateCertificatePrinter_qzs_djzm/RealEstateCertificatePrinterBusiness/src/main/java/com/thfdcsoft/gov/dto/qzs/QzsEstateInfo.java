package com.thfdcsoft.gov.dto.qzs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QzsEstateInfo {
    /**业务号*/
    @JsonProperty(value = "businessNumber")
    private String businessNumber;
    /**登簿时间*/
    @JsonProperty(value = "bookTime")
    private String bookTime;
    /**不动产权证书号*/
    @JsonProperty(value = "certNumber")
    private String certNumber;
    /**权利人*/
    @JsonProperty(value = "obligee")
    private String obligee;
    /**共有情况*/
    @JsonProperty(value = "co_ownershipCircumstance")
    private String co_ownershipCircumstance;
    /**坐落*/
    @JsonProperty(value = "located")
    private String located;
    /**不动产单元号*/
    @JsonProperty(value = "unitNumber")
    private String unitNumber;
    /**权利类型*/
    @JsonProperty(value = "rightTypes")
    private String rightTypes;
    /**权利性质*/
    @JsonProperty(value = "rightNature")
    private String rightNature;
    /**用途*/
    @JsonProperty(value = "application")
    private String application;
    /**面积*/
    @JsonProperty(value = "area")
    private String area;
    /**使用期限*/
    @JsonProperty(value = "serviceLife")
    private String serviceLife;
    /**权利其他情况*/
    @JsonProperty(value = "other")
    private String other;
    /**附记*/
    @JsonProperty(value = "excursus")
    private String excursus;
    /**分布图 base64*/
    @JsonProperty(value = "fenbutu")
    private String fenbutu;
    /**宗地图 base64*/
    @JsonProperty(value = "zongditu")
    private String zongditu;
    /**二维码*/
    @JsonProperty(value = "ewm")
    private String ewm;

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getCertNumber() {
        return certNumber;
    }

    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber;
    }

    public String getObligee() {
        return obligee;
    }

    public void setObligee(String obligee) {
        this.obligee = obligee;
    }

    public String getCo_ownershipCircumstance() {
        return co_ownershipCircumstance;
    }

    public void setCo_ownershipCircumstance(String co_ownershipCircumstance) {
        this.co_ownershipCircumstance = co_ownershipCircumstance;
    }

    public String getLocated() {
        return located;
    }

    public void setLocated(String located) {
        this.located = located;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getRightTypes() {
        return rightTypes;
    }

    public void setRightTypes(String rightTypes) {
        this.rightTypes = rightTypes;
    }

    public String getRightNature() {
        return rightNature;
    }

    public void setRightNature(String rightNature) {
        this.rightNature = rightNature;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(String serviceLife) {
        this.serviceLife = serviceLife;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getExcursus() {
        return excursus;
    }

    public void setExcursus(String excursus) {
        this.excursus = excursus;
    }

    public String getFenbutu() {
        return fenbutu;
    }

    public void setFenbutu(String fenbutu) {
        this.fenbutu = fenbutu;
    }

    public String getZongditu() {
        return zongditu;
    }

    public void setZongditu(String zongditu) {
        this.zongditu = zongditu;
    }

    public String getEwm() {
        return ewm;
    }

    public void setEwm(String ewm) {
        this.ewm = ewm;
    }

    @Override
    public String toString() {
        return "QzsQueryResultDto{" +
                "businessNumber='" + businessNumber + '\'' +
                ", bookTime='" + bookTime + '\'' +
                ", certNumber='" + certNumber + '\'' +
                ", obligee='" + obligee + '\'' +
                ", co_ownershipCircumstance='" + co_ownershipCircumstance + '\'' +
                ", located='" + located + '\'' +
                ", unitNumber='" + unitNumber + '\'' +
                ", rightTypes='" + rightTypes + '\'' +
                ", rightNature='" + rightNature + '\'' +
                ", application='" + application + '\'' +
                ", area='" + area + '\'' +
                ", serviceLife='" + serviceLife + '\'' +
                ", other='" + other + '\'' +
                ", excursus='" + excursus + '\'' +
                ", fenbutu='" + fenbutu + '\'' +
                ", zongditu='" + zongditu + '\'' +
                ", ewm='" + ewm + '\'' +
                '}';
    }
}
