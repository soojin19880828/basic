package com.thfdcsoft.estate.printer.dto.rspn;

public class FaceDetectRspn {

	private String result;// 响应结果[00:成功;01:失败]

	private String detPicPath;// 识别照片地址
	
	private String detPicFile;// 识别照片文件Base64格式
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDetPicPath() {
		return detPicPath;
	}

	public void setDetPicPath(String detPicPath) {
		this.detPicPath = detPicPath;
	}

	public String getDetPicFile() {
		return detPicFile;
	}

	public void setDetPicFile(String detPicFile) {
		this.detPicFile = detPicFile;
	}
	
}
