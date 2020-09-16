package com.thfdcsoft.gov.dto.qzs;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 权利人信息对象类
 *
 */
public class QzsQlrInfo {
	/** 权利人名称 */
	@JsonProperty(value = "MC")
	private String mc;

	/** 证件类别 */
	@JsonProperty(value = "ZJLB")
	private String zjlb;

	/** 证件号 */
	@JsonProperty(value = "ZJH")
	private String zjh;

	/** 共有比例 */
	@JsonProperty(value = "GYBL")
	private String gybl;

	/** 共有关系 */
	@JsonProperty(value = "GYGX")
	private String gygx;

	/** 证书编号 （YWLX为5、6、7、8时存在） */
	@JsonProperty(value = "ZSBH")
	private String zsbh;

	/** 是否打证 1打证 0不打证 （YWLX为5、6、7、8时存在） */
	@JsonProperty(value = "SFDZ")
	private String sfdz;

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
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

	public String getZsbh() {
		return zsbh;
	}

	public void setZsbh(String zsbh) {
		this.zsbh = zsbh;
	}

	public String getSfdz() {
		return sfdz;
	}

	public void setSfdz(String sfdz) {
		this.sfdz = sfdz;
	}

	@Override
	public String toString() {
		return "QlrInfo{" +
				"mc='" + mc + '\'' +
				", zjlb='" + zjlb + '\'' +
				", zjh='" + zjh + '\'' +
				", gybl='" + gybl + '\'' +
				", gygx='" + gygx + '\'' +
				", zsbh='" + zsbh + '\'' +
				", sfdz='" + sfdz + '\'' +
				'}';
	}
}
