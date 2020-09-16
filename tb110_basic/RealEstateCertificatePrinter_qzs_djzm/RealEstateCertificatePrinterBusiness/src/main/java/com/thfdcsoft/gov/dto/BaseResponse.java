package com.thfdcsoft.gov.dto;

import com.thfdcsoft.gov.dto.djzm.DjzmQueryResultDto;

import java.util.List;
/**
　　* @description: BaseResponse基本响应参数
　　* @author 高拓
　　* @date 2020/9/10 10:08
　　*/
public class BaseResponse {
	private List<DjzmQueryResultDto> data;
	
	private int result;
	
	private String msg;

	public List<DjzmQueryResultDto> getData() {
		return data;
	}

	public void setData(List<DjzmQueryResultDto> data) {
		this.data = data;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
