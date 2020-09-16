package com.thfdcsoft.estate.printer.dto;

/**
 * 不动产登记证明信息对象类
 * 
 * @author 张嘉琪
 *
 */
public class EstateInfo {
	
	// 业务受理号
	private String busiNumber;

	// 不动产单元号
	private String unitNumber;

	// 不动产权证书号
	private String certNumber;

	// 附记
	private String notes;

	// 权利其它情况
	private String otherCases;

	// 坐落
	private String located;

	// 证明权利或事项[业务登记类型]
	private String busiType;

	// 权利人
	private String obligee;

	// 义务人
	private String obligor;

	// 登簿时间
	private String registerTime;
	
	//证书二维码
	private String ewm;
	
	public String getBusiNumber() {
		return busiNumber;
	}

	public void setBusiNumber(String busiNumber) {
		this.busiNumber = busiNumber;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getCertNumber() {
		return certNumber;
	}

	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOtherCases() {
		return otherCases;
	}

	public void setOtherCases(String otherCases) {
		this.otherCases = otherCases;
	}

	public String getLocated() {
		return located;
	}

	public void setLocated(String located) {
		this.located = located;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getObligee() {
		return obligee;
	}

	public void setObligee(String obligee) {
		this.obligee = obligee;
	}

	public String getObligor() {
		return obligor;
	}

	public void setObligor(String obligor) {
		this.obligor = obligor;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	
	

	public String getEwm() {
		return ewm;
	}

	public void setEwm(String ewm) {
		this.ewm = ewm;
	}

	@Override
	public String toString() {
		return "EstateInfo [unitNumber=" + unitNumber + ", certNumber=" + certNumber + ", notes=" + notes
				+ ", otherCases=" + otherCases + ", located=" + located + ", busiType=" + busiType + ", obligee="
				+ obligee + ", obligor=" + obligor + ", registerTime=" + registerTime + ", ewm=" + ewm + "]";
	}

}
