package com.thfdcsoft.gov.dto.qzs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thfdcsoft.gov.dto.TdytInfo;

import java.util.Arrays;
import java.util.List;

/**
 * 不动产单元信息对象类(YWLX=2或者YWLX=3或者YWLX=4才有值，否则为空)
 *
 */
public class QzsBdcdyxxInfo {
	
	/**不动产单元号*/
	@JsonProperty(value = "BDCDYH")
	private String bdcdyh;

	/**项目*/
	@JsonProperty(value = "XM")
	private String xm;

	/**楼幢*/
	@JsonProperty(value = "LD")
	private String ld;

	/**房号*/
	@JsonProperty(value = "FH")
	private String fh;

	/**面积*/
	@JsonProperty(value = "MJ")
	private Double mj;

	/**面积单位(1、平方米 2、亩 3、公顷)*/
	@JsonProperty(value = "MJDW")
	private int mjdw;

	/**面积单位*/
	@JsonProperty(value = "YT")
	private String yt;

	/**抵押不动产权证号*/
	@JsonProperty(value = "BDCQZ")
	private String bdcqz;

	/**抵押不动产权人*/
	@JsonProperty(value = "BDCQR")
	private String bdcqr;

	/**抵押土地证号*/
	@JsonProperty(value = "TDZH")
	private String tdzh;

	/**坐落*/
	@JsonProperty(value = "ZL")
	private String zl;

	/**不动产类型（1土地 2房屋 3构筑物）*/
	@JsonProperty(value = "BDCLX")
	private int bdclx;

	/**所在层*/
	@JsonProperty(value = "SZC")
	private String szc;

	/**总层数*/
	@JsonProperty(value = "ZCS")
	private String zcs;

	/**建筑结构*/
	@JsonProperty(value = "JZJG")
	private String jzjg;

	/**宗地面积*/
	@JsonProperty(value = "ZDMJ")
	private String zdmj;

	/**房屋套内面积*/
	@JsonProperty(value = "FWTNMJ")
	private String fwtnmj;

	/**房屋分摊面积*/
	@JsonProperty(value = "FWFTMJ")
	private String fwftmj;

	/**原不动产权证号(不动产权证时存在)*/
	@JsonProperty(value = "YBDCQZH")
	private String ybdcqzh;

	/**土地性质*/
	@JsonProperty(value = "TDXZ")
	private String tdxz;

	/**房屋性质*/
	@JsonProperty(value = "FWXZ")
	private String fwxz;

	/**独用土地面积*/
	@JsonProperty(value = "DYTDMJ")
	private String dytdmj;

	/**分摊土地面积*/
	@JsonProperty(value = "FTTDMJ")
	private String fttdmj;

	/**土地用途*/
	@JsonProperty(value = "TDYT")
	private List<TdytInfo> tdyt;

	/**房产平面图  Base64字符串,权利类型为5时有效*/
	@JsonProperty(value = "FCPMT")
	private String[] fcpmt;

	/**宗地图  Base64字符串,权利类型为5、6、7、8时有效*/
	@JsonProperty(value = "ZDT")
	private String[] zdt;

	/**建成年份*/
	@JsonProperty(value = "JCNF")
	private String jcnf;

	/**建成日期*/
	@JsonProperty(value = "JCRQ")
	private String jcrq;

	public String getBdcdyh() {
		return bdcdyh;
	}

	public void setBdcdyh(String bdcdyh) {
		this.bdcdyh = bdcdyh;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getLd() {
		return ld;
	}

	public void setLd(String ld) {
		this.ld = ld;
	}

	public String getFh() {
		return fh;
	}

	public void setFh(String fh) {
		this.fh = fh;
	}

	public Double getMj() {
		return mj;
	}

	public void setMj(Double mj) {
		this.mj = mj;
	}

	public int getMjdw() {
		return mjdw;
	}

	public void setMjdw(int mjdw) {
		this.mjdw = mjdw;
	}

	public String getYt() {
		return yt;
	}

	public void setYt(String yt) {
		this.yt = yt;
	}

	public String getBdcqz() {
		return bdcqz;
	}

	public void setBdcqz(String bdcqz) {
		this.bdcqz = bdcqz;
	}

	public String getBdcqr() {
		return bdcqr;
	}

	public void setBdcqr(String bdcqr) {
		this.bdcqr = bdcqr;
	}

	public String getTdzh() {
		return tdzh;
	}

	public void setTdzh(String tdzh) {
		this.tdzh = tdzh;
	}

	public String getZl() {
		return zl;
	}

	public void setZl(String zl) {
		this.zl = zl;
	}

	public int getBdclx() {
		return bdclx;
	}

	public void setBdclx(int bdclx) {
		this.bdclx = bdclx;
	}

	public String getSzc() {
		return szc;
	}

	public void setSzc(String szc) {
		this.szc = szc;
	}

	public String getZcs() {
		return zcs;
	}

	public void setZcs(String zcs) {
		this.zcs = zcs;
	}

	public String getJzjg() {
		return jzjg;
	}

	public void setJzjg(String jzjg) {
		this.jzjg = jzjg;
	}

	public String getZdmj() {
		return zdmj;
	}

	public void setZdmj(String zdmj) {
		this.zdmj = zdmj;
	}

	public String getFwtnmj() {
		return fwtnmj;
	}

	public void setFwtnmj(String fwtnmj) {
		this.fwtnmj = fwtnmj;
	}

	public String getFwftmj() {
		return fwftmj;
	}

	public void setFwftmj(String fwftmj) {
		this.fwftmj = fwftmj;
	}

	public String getYbdcqzh() {
		return ybdcqzh;
	}

	public void setYbdcqzh(String ybdcqzh) {
		this.ybdcqzh = ybdcqzh;
	}

	public String getTdxz() {
		return tdxz;
	}

	public void setTdxz(String tdxz) {
		this.tdxz = tdxz;
	}

	public String getFwxz() {
		return fwxz;
	}

	public void setFwxz(String fwxz) {
		this.fwxz = fwxz;
	}

	public String getDytdmj() {
		return dytdmj;
	}

	public void setDytdmj(String dytdmj) {
		this.dytdmj = dytdmj;
	}

	public String getFttdmj() {
		return fttdmj;
	}

	public void setFttdmj(String fttdmj) {
		this.fttdmj = fttdmj;
	}

	public List<TdytInfo> getTdyt() {
		return tdyt;
	}

	public void setTdyt(List<TdytInfo> tdyt) {
		this.tdyt = tdyt;
	}

	public String[] getFcpmt() {
		return fcpmt;
	}

	public void setFcpmt(String[] fcpmt) {
		this.fcpmt = fcpmt;
	}

	public String[] getZdt() {
		return zdt;
	}

	public void setZdt(String[] zdt) {
		this.zdt = zdt;
	}

	public String getJcnf() {
		return jcnf;
	}

	public void setJcnf(String jcnf) {
		this.jcnf = jcnf;
	}

	public String getJcrq() {
		return jcrq;
	}

	public void setJcrq(String jcrq) {
		this.jcrq = jcrq;
	}

	@Override
	public String toString() {
		return "BdcdyxxInfo{" +
				"bdcdyh='" + bdcdyh + '\'' +
				", xm='" + xm + '\'' +
				", ld='" + ld + '\'' +
				", fh='" + fh + '\'' +
				", mj=" + mj +
				", mjdw=" + mjdw +
				", yt='" + yt + '\'' +
				", bdcqz='" + bdcqz + '\'' +
				", bdcqr='" + bdcqr + '\'' +
				", tdzh='" + tdzh + '\'' +
				", zl='" + zl + '\'' +
				", bdclx=" + bdclx +
				", szc='" + szc + '\'' +
				", zcs='" + zcs + '\'' +
				", jzjg='" + jzjg + '\'' +
				", zdmj='" + zdmj + '\'' +
				", fwtnmj='" + fwtnmj + '\'' +
				", fwftmj='" + fwftmj + '\'' +
				", ybdcqzh='" + ybdcqzh + '\'' +
				", tdxz='" + tdxz + '\'' +
				", fwxz='" + fwxz + '\'' +
				", dytdmj='" + dytdmj + '\'' +
				", fttdmj='" + fttdmj + '\'' +
				", tdyt=" + tdyt +
				", fcpmt=" + Arrays.toString(fcpmt) +
				", zdt=" + Arrays.toString(zdt) +
				", jcnf='" + jcnf + '\'' +
				", jcrq='" + jcrq + '\'' +
				'}';
	}

}
