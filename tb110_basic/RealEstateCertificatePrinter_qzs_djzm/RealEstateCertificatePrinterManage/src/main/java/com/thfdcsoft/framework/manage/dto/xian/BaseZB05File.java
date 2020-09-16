package com.thfdcsoft.framework.manage.dto.xian;

/**
 * 打印过程文件对象<br>
 * 参见《IF001_天恒智能不动产智能终端设备接口设计说明书_V1.2》5.3.1.2
 * 
 * @author 张嘉琪
 * @date 2019年1月5日
 */
public class BaseZB05File {

	// 文件名
	private String fileName;

	// 文件类型
	private String fileType;

	// 文件后缀名
	private String suffixName;

	// BASE64格式文件数据
	private String fileData;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getSuffixName() {
		return suffixName;
	}

	public void setSuffixName(String suffixName) {
		this.suffixName = suffixName;
	}

	public String getFileData() {
		return fileData;
	}

	public void setFileData(String fileData) {
		this.fileData = fileData;
	}

}
