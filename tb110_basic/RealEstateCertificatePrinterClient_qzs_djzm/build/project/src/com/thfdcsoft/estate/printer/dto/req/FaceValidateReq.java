package com.thfdcsoft.estate.printer.dto.req;

public class FaceValidateReq {
	
	private String idNumber;// 身份证号
	
	private String idPicPath;// 身份证照片地址
	
	private String idPicFile;// 身份证照片文件Base64格式
	
	private String detPicPath;// 识别照片地址
	
	private String detPicFile;// 识别照片文件Base64格式

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdPicPath() {
		return idPicPath;
	}

	public void setIdPicPath(String idPicPath) {
		this.idPicPath = idPicPath;
	}

	public String getIdPicFile() {
		return idPicFile;
	}

	public void setIdPicFile(String idPicFile) {
		this.idPicFile = idPicFile;
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
