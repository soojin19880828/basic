package com.thfdcsoft.framework.manage.dto.xian;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Resend {

	private String fullName;//领证人姓名
	
	private String idNumer;//领证人身份证号码
	
	@JsonProperty("XZQ")
	private String XZQ      ;//行政区编码（市级610100）
	
	private List<ResendFile> fileList ;//图像文件流
	
	private List<ResendCert> certList ;//证书list

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIdNumer() {
		return idNumer;
	}

	public void setIdNumer(String idNumer) {
		this.idNumer = idNumer;
	}

	@JsonProperty("XZQ")
	public String getXZQ() {
		return XZQ;
	}

	public void setXZQ(String xZQ) {
		XZQ = xZQ;
	}

	public List<ResendFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ResendFile> fileList) {
		this.fileList = fileList;
	}

	public List<ResendCert> getCertList() {
		return certList;
	}

	public void setCertList(List<ResendCert> certList) {
		this.certList = certList;
	}
	
	
	
}
