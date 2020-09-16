package com.whminwei.dto.rspn;

/**
 * @author 肖银
 * 2020年8月28日
 */
public class HttpResult {

	/** 请求处理结果 */
	private Boolean result;

	/** 请求异常信息 */
	private String respMsg;

	/** 请求异常码 */
	private String errCode;
	
	/** 请求响应数据 */
	private String data;

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public HttpResult(Boolean result) {
		super();
		this.result = result;
	}
	
}
