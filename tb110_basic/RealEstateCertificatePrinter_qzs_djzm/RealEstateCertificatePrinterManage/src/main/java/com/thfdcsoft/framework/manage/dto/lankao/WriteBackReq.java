package com.thfdcsoft.framework.manage.dto.lankao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WriteBackReq {
/**
 * 不动产证书打印回写信息类
 * 
 */
	//授权码
	@JsonProperty(value = "YZM")
	private String yzm;
	
	@JsonProperty(value = "YJM")
	private String yjm;
	
	@JsonProperty(value = "DATA")
	private List<WriteBackDataInfo> data;

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

	public List<WriteBackDataInfo> getData() {
		return data;
	}

	public void setData(List<WriteBackDataInfo> data) {
		this.data = data;
	}

}
