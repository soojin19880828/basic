package com.thfdcsoft.gov.dto.djzm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryRequest {
	
	@JsonProperty(value = "YZM")//验证码
	private String yzm;
     
	@JsonProperty(value = "YJM")//硬件码
	private String yjm;
	
	@JsonProperty(value = "SFZH")//身份证号
	private String sfzh;

	public String getYzm() {
		return yzm;
	}

	public void setYzm(String yzm) {
		this.yzm = yzm;
	}

	public String getYjm() {
		return yjm;
	}

	public void setYjm(String yjm) {
		this.yjm = yjm;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

}
