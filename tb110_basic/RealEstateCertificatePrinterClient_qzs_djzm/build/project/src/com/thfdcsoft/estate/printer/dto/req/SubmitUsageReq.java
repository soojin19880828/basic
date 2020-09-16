package com.thfdcsoft.estate.printer.dto.req;

/**
 * 领证代理人[记录]信息
 * 
 * @author 张嘉琪
 *
 */
public class SubmitUsageReq {

	// 身份证号
	private String idNumber;
	
	// 姓名
	private String fullName;

	// 设备编号
	private String deviceNumber;

	// 身份证照片
	private String idPic;

	// 人脸识别照片
	private String detPic;
	
	//日志地址
	private String logPath;
	
	

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getIdPic() {
		return idPic;
	}

	public void setIdPic(String idPic) {
		this.idPic = idPic;
	}

	public String getDetPic() {
		return detPic;
	}

	public void setDetPic(String detPic) {
		this.detPic = detPic;
	}

	@Override
	public String toString() {
		return "SubmitUsageReq{" +
				"idNumber='" + idNumber + '\'' +
				", fullName='" + fullName + '\'' +
				", deviceNumber='" + deviceNumber + '\'' +
				", idPic='" + idPic + '\'' +
				", detPic='" + detPic + '\'' +
				", logPath='" + logPath + '\'' +
				'}';
	}
}
