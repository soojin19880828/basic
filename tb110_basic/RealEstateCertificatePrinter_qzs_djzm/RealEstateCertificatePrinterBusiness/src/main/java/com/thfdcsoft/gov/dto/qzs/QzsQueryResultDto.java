package com.thfdcsoft.gov.dto.qzs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thfdcsoft.gov.dto.DybdcqrInfo;
import com.thfdcsoft.gov.dto.DyxxInfo;
import com.thfdcsoft.gov.dto.YwrInfo;

import java.util.List;

/**
 * @author 高拓
 * @description: 权证书返回信息封装
 * @date 2020/9/9 18:20
 */
public class QzsQueryResultDto {
    /**受理编号*/
    @JsonProperty(value = "SLBH")
    private String slbh;

    /**业务综合号*/
    @JsonProperty(value = "YWZH")
    private String ywzh;

    /**监管证号*/
    @JsonProperty(value = "JGZH")
    private String jgzh;

    /**权力类型*/
    @JsonProperty(value = "QLLX")
    private String qllx;

    /**权力性质*/
    @JsonProperty(value = "QLXZ")
    private String qlxz;

    /**业务类型(1、预告登记2、抵押权预告登记3、不动产权抵押 4、在建建筑物抵押 5、不动产权证_房地产权证  6、不动产权证_土地证
    7、不动产权证_林权证
    8、不动产权证_农用地)*/
    @JsonProperty(value = "YWLX")
    private int ywlx;

    /**登记原因*/
    @JsonProperty(value = "DJYY")
    private String djyy;

    /**证书编号*/
    @JsonProperty(value = "ZSBH")
    private String zsbh;

    /**印刷编号*/
    @JsonProperty(value = "YSBH")
    private String ysbh;

    /**证书二维码*/
    @JsonProperty(value = "EWM")
    private String ewm;

    /**权利人*/
    @JsonProperty(value = "QLR")
    private List<QzsQlrInfo> qlr;

    /**义务人*/
    @JsonProperty(value = "YWR")
    private List<YwrInfo> ywr;

    /**登记坐落*/
    @JsonProperty(value = "DJZL")
    private String djzl;

    /**登记机构*/
    @JsonProperty(value = "DJJG")
    private String djjg;

    /**发证时间*/
    @JsonProperty(value = "FZSJ")
    private String fzsj;

    /**登记时间*/
    @JsonProperty(value = "DJSJ")
    private String djsj;

    /**共有情况*/
    @JsonProperty(value = "GYQK")
    private String gyqk;

    /**房屋价值*/
    @JsonProperty(value = "FWJZ")
    private Double fwjz;

    /**备注*/
    @JsonProperty(value = "BZ")
    private String bz;

    /**其他事项*/
    @JsonProperty(value = "QTSX")
    private String qtsx;

    /**抵押信息*/
    @JsonProperty(value = "DYXX")
    private DyxxInfo dyxx;

    /**抵押不动产权人*/
    @JsonProperty(value = "DYBDCQR")
    private List<DybdcqrInfo> dybdcqr;

    /**不动产单元信息*/
    @JsonProperty(value = "BDCDYXX")
    private List<QzsBdcdyxxInfo> bdcdyxx;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getYwzh() {
        return ywzh;
    }

    public void setYwzh(String ywzh) {
        this.ywzh = ywzh;
    }

    public String getJgzh() {
        return jgzh;
    }

    public void setJgzh(String jgzh) {
        this.jgzh = jgzh;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public int getYwlx() {
        return ywlx;
    }

    public void setYwlx(int ywlx) {
        this.ywlx = ywlx;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getZsbh() {
        return zsbh;
    }

    public void setZsbh(String zsbh) {
        this.zsbh = zsbh;
    }

    public String getYsbh() {
        return ysbh;
    }

    public void setYsbh(String ysbh) {
        this.ysbh = ysbh;
    }

    public String getEwm() {
        return ewm;
    }

    public void setEwm(String ewm) {
        this.ewm = ewm;
    }

    public List<QzsQlrInfo> getQlr() {
        return qlr;
    }

    public void setQlr(List<QzsQlrInfo> qlr) {
        this.qlr = qlr;
    }

    public List<YwrInfo> getYwr() {
        return ywr;
    }

    public void setYwr(List<YwrInfo> ywr) {
        this.ywr = ywr;
    }

    public String getDjzl() {
        return djzl;
    }

    public void setDjzl(String djzl) {
        this.djzl = djzl;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getFzsj() {
        return fzsj;
    }

    public void setFzsj(String fzsj) {
        this.fzsj = fzsj;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public Double getFwjz() {
        return fwjz;
    }

    public void setFwjz(Double fwjz) {
        this.fwjz = fwjz;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public DyxxInfo getDyxx() {
        return dyxx;
    }

    public void setDyxx(DyxxInfo dyxx) {
        this.dyxx = dyxx;
    }

    public List<DybdcqrInfo> getDybdcqr() {
        return dybdcqr;
    }

    public void setDybdcqr(List<DybdcqrInfo> dybdcqr) {
        this.dybdcqr = dybdcqr;
    }

    public List<QzsBdcdyxxInfo> getBdcdyxx() {
        return bdcdyxx;
    }

    public void setBdcdyxx(List<QzsBdcdyxxInfo> bdcdyxx) {
        this.bdcdyxx = bdcdyxx;
    }

    @Override
    public String toString() {
        return "PYQueryResultDto{" +
                "slbh='" + slbh + '\'' +
                ", ywzh='" + ywzh + '\'' +
                ", jgzh='" + jgzh + '\'' +
                ", qllx='" + qllx + '\'' +
                ", qlxz='" + qlxz + '\'' +
                ", ywlx=" + ywlx +
                ", djyy='" + djyy + '\'' +
                ", zsbh='" + zsbh + '\'' +
                ", ysbh='" + ysbh + '\'' +
                ", ewm='" + ewm + '\'' +
                ", qlr=" + qlr +
                ", ywr=" + ywr +
                ", djzl='" + djzl + '\'' +
                ", djjg='" + djjg + '\'' +
                ", fzsj='" + fzsj + '\'' +
                ", djsj='" + djsj + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", fwjz=" + fwjz +
                ", bz='" + bz + '\'' +
                ", dyxx=" + dyxx +
                ", dybdcqr=" + dybdcqr +
                ", bdcdyxx=" + bdcdyxx +
                '}';
    }
}
