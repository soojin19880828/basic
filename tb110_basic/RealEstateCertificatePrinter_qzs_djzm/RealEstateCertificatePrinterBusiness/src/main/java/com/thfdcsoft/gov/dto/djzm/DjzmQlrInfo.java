package com.thfdcsoft.gov.dto.djzm;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
　　* @description: 权利人信息对象类(登记证明)
　　* @author 高拓
　　* @date 2020/9/10 10:05
　　*/
public class DjzmQlrInfo {
	//权利人名称
	@JsonProperty(value = "MC")
	private String mc;

	//证件类别
	@JsonProperty(value = "ZJLB")
	private String zjlb;
	
	//证件号
	@JsonProperty(value = "ZJH")
	private String zjh;
	
	//共有比例
	@JsonProperty(value = "GYBL")
	private String gybl;
	
	//共有关系
	@JsonProperty(value = "GYGX")
	private String gygx;

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
	
}
