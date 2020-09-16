package com.thfdcsoft.framework.manage.dto.lankao;
/**
 * WriteBackResponse基本响应参数
 * 
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
