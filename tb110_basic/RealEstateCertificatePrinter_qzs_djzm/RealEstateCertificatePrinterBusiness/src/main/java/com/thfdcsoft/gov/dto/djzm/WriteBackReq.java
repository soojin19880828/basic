package com.thfdcsoft.gov.dto.djzm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thfdcsoft.gov.dto.djzm.WriteBackDataInfo;

import java.util.List;
/**
 　　* @description: 不动产证明打印回写信息类
 　　* @author 高拓
 　　* @date 2020/9/10 10:14
 　　*/
public class WriteBackReq {
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
