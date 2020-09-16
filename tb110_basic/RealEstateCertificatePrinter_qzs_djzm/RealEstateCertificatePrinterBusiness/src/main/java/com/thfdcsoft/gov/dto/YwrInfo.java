package com.thfdcsoft.gov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 义务人信息对象类
 *
 */
public class YwrInfo {
	/** 义务人名称 */
	@JsonProperty(value = "MC")
	private String mc;

	/** 证件类别 */
	@JsonProperty(value = "ZJLB")
	private String zjlb;

	/** 证件号 */
	@JsonProperty(value = "ZJH")
	private String zjh;

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
	

}
