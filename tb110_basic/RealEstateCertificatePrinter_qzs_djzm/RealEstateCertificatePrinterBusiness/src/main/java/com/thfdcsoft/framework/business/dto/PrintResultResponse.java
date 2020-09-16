package com.thfdcsoft.framework.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrintResultResponse {

	@JsonProperty(value = "Result")
	private boolean Result;
	
	@JsonProperty(value = "Msg")
	private String msg;
	
	@JsonProperty(value = "Data")
	private String data;

	public boolean getResult() {
		return Result;
	}

	public void setResult(boolean result) {
		Result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
