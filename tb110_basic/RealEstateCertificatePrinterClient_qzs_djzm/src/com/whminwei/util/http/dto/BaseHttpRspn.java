package com.whminwei.util.http.dto;

/**
 * Http请求返回对象
 * 
 * @author 张嘉琪
 * @date 2017年11月15日上午11:51:13
 */
public class BaseHttpRspn {
	
	public BaseHttpRspn(Boolean result) {
		this.setResult(result);
	}

	public BaseHttpRspn(Boolean result, String respJson) {
		this.setResult(result);
		this.setRespJson(respJson);
	}
	
	public BaseHttpRspn(Boolean result, Object respObj) {
		this.setResult(result);
		this.setRespObj(respObj);
	}
	
	public BaseHttpRspn(Boolean result, String respObj, String respMsg) {
		this.setResult(result);
		this.setRespObj(respObj);
		this.setRespMsg(respMsg);
	}

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
}
