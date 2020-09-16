package com.thfdcsoft.gov.dto.djzm;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 不动产单元信息对象类(YWLX=2或者YWLX=3或者YWLX=4才有值，否则为空)
 *
 */
public class DjzmBdcdyxxInfo {
	
	//不动产单元号
	@JsonProperty(value = "BDCDYH")
	private String bdcdyh;

	//项目
	@JsonProperty(value = "XM")
	private String xm;
 
	//楼幢
	@JsonProperty(value = "LD")
	private String ld;
	
	//房号
	@JsonProperty(value = "FH")
	private String fh;
	
	//面积
	@JsonProperty(value = "MJ")
	private Double mj;
	
	//面积单位(1、平方米 2、亩 3、公顷)
	@JsonProperty(value = "MJDW")
	private int mjdw;

	//宗地面积
	@JsonProperty(value = "ZDMJ")
	private String zdmj;
	
	//用途
	@JsonProperty(value = "YT")
	private String yt;
	
	//抵押不动产权证号
	@JsonProperty(value = "BDCQZ")
	private String bdcqz;

	//抵押不动产权人
	@JsonProperty(value = "BDCQR")
	private String bdcqr;

	//抵押土地证号
	@JsonProperty(value = "TDZH")
	private String tdzh;
	
	//坐落
	@JsonProperty(value = "ZL")
	private String zl;

	//不动产类型（1土地 2房屋 3构筑物）
	@JsonProperty(value = "BDCLX")
	private int bdclx;

	//所在层
	@JsonProperty(value = "SZC")
	private String szc;

	//总层数
	@JsonProperty(value = "ZCS")
	private String zcs;

	//建筑结构
	@JsonProperty(value = "JZJG")
	private String jzjg;

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
}
