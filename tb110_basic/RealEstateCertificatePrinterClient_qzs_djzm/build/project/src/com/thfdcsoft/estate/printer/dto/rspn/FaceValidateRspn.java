package com.thfdcsoft.estate.printer.dto.rspn;

public class FaceValidateRspn {
	
	private String result;// 响应结果[00:成功;01:失败]
	
	private String degree;// 人脸比对相似度

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

}
