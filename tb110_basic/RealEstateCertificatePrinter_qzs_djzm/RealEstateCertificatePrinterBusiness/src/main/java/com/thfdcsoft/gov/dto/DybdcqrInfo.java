package com.thfdcsoft.gov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 抵押不动产权人信息对象类(YWLX=2或者YWLX=3或者YWLX=4才有值，否则为空)
 *
 */
public class DybdcqrInfo {
	/**抵押不动产权人*/
	@JsonProperty(value = "BDCQR")
	private String bdcqr;

	/**抵押不动产权证号*/
	@JsonProperty(value = "BDCQZH")
	private String bdcqzh;

	/**证件类别*/
	@JsonProperty(value = "ZJLB")
	private String zjlb;

	/**证件号*/
	@JsonProperty(value = "ZJH")
	private String zjh;

	/**共有比例*/
	@JsonProperty(value = "GYBL")
	private String gybl;

	/**共有关系*/
	@JsonProperty(value = "GYGX")
	private String gygx;

	public String getBdcqr() {
		return bdcqr;
	}

	public void setBdcqr(String bdcqr) {
		this.bdcqr = bdcqr;
	}

	public String getBdcqzh() {
		return bdcqzh;
	}

	public void setBdcqzh(String bdcqzh) {
		this.bdcqzh = bdcqzh;
	}

	public String getZjlb() {
		return zjlb;
	}

	public void setZjlb(String zjlb) {
		this.zjlb = zjlb;
	}

	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh;
	}

	public String getGybl() {
		return gybl;
	}

	public void setGybl(String gybl) {
		this.gybl = gybl;
	}

	public String getGygx() {
		return gygx;
	}

	public void setGygx(String gygx) {
		this.gygx = gygx;
	}


	@Override
	public String toString() {
		return "DybdcqrInfo{" +
				"bdcqr='" + bdcqr + '\'' +
				", bdcqzh='" + bdcqzh + '\'' +
				", zjlb='" + zjlb + '\'' +
				", zjh='" + zjh + '\'' +
				", gybl='" + gybl + '\'' +
				", gygx='" + gygx + '\'' +
				'}';
	}
}
