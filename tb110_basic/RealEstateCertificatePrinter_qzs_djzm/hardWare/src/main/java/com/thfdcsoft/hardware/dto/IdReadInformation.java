package com.thfdcsoft.hardware.dto;

public class IdReadInformation {

	private String fullName;   //姓名
	
	private String sex;  //性别
	
	private String nation;  //国家
	
	private String idNumber;  //身份证号码
	
	private String idPicPath;  //身份证图片位置
	
	private String ExpireDate; //有效截至日期

	public String getFullName() {
		return fullName;
	}

	public String getExpireDate() {
		return ExpireDate;
	}

	public void setExpireDate(String expireDate) {
		ExpireDate = expireDate;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

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
	
}
