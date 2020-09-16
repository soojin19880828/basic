package com.thfdcsoft.gov.dto.djzm;
/**
 　　* @description: 登记证明回传基本响应参数
 　　* @author 高拓
 　　* @date 2020/9/10 10:16
 　　*/
public class WriteBackResponse {
    private String data;
	
	private int result;
	
	private String msg;

	public String getData() {
		return data;
	}

	public void setData(String data) {
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
