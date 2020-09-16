package com.whminwei.view.pages;

public class IdReadInformation {
	/**姓名*/
	private String fullName;
	/**性别*/
	private String sex;
	/**国家*/
	private String nation;
	/**身份证号码*/
	private String idNumber;
	/**身份证图片位置*/
	private String idPicPath;
	/**有效截至日期*/
	private String ExpireDate;
	
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
	
	public IdReadInformation() {
		super();
	}

	public IdReadInformation(String fullName, String sex, String nation, String idNumber, String idPicPath,
			String expireDate) {
		super();
		this.fullName = fullName;
		this.sex = sex;
		this.nation = nation;
		this.idNumber = idNumber;
		this.idPicPath = idPicPath;
		ExpireDate = expireDate;
	}

	@Override
	public String toString() {
		return "IdReadInformation [fullName=" + fullName + ", sex=" + sex + ", nation=" + nation + ", idNumber="
				+ idNumber + ", idPicPath=" + idPicPath + ", ExpireDate=" + ExpireDate + "]";
	}
	
}
