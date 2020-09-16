package com.whminwei.dto.req;

public class FaceDetectReq {
	
	private String idNumber;// 身份证号

	private String camPicPath;// 拍摄照片地址
	
	private String camPicFile;// 拍摄照片文件Base64格式

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCamPicPath() {
		return camPicPath;
	}

	public void setCamPicPath(String camPicPath) {
		this.camPicPath = camPicPath;
	}

	public String getCamPicFile() {
		return camPicFile;
	}

	public void setCamPicFile(String camPicFile) {
		this.camPicFile = camPicFile;
	}

}
