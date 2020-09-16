package com.thfdcsoft.estate.printer.dto;

/**
 * Http请求返回对象
 * 
 * @author 张嘉琪
 * @date 2017年11月15日上午11:51:13
 */
public class HttpReturnDto {
	
	// 业务处理结果
	private Boolean result;

	// 返回消息-Json
	private String respJson;
	
	// 其它返回参数
	private Object respObj;

	// 返回消息
	private String respMsg;

	public Boolean isResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getRespJson() {
		return respJson;
	}

	public void setRespJson(String respJson) {
		this.respJson = respJson;
	}

	public Object getRespObj() {
		return respObj;
	}

	public void setRespObj(Object respObj) {
		this.respObj = respObj;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	@Override
	public String toString() {
		return "HttpReturnDto [result=" + result + ", respJson=" + respJson + ", respObj=" + respObj + ", respMsg="
				+ respMsg + "]";
	}
	
	
}
