package com.thfdcsoft.framework.manage.dto.xian;

import java.util.List;

/**
 * 登记证明对象<br>
 * 参见《IF001_天恒智能不动产智能终端设备接口设计说明书_V1.3》5.3.1.1
 * 
 * @author 张嘉琪
 * @date 2019年1月6日
 */

public class BaseZB05Cert {
	//业务编号
	private String businessNo;
	
	//收件编号
	private String sjbh;
	
	// 登簿时间[yyyy-MM-dd]
	private String bookTime;

	// 不动产登记证明号
	private String certNumber;

	// 证明权利或事项
	private String bizType;

	// 权利人
	private String obligee;

	// 义务人
	private String obligor;

	// 坐落
	private String located;

	// 不动产单元号
	private String unitNumber;

	// 其他
	private String other;

	// 附记
	private String notes;

	// 印刷序列号
	private String seqNumber;

	// 打印过程文件列表
	private List<BaseZB05File> fileList;
	
	//登记时间
	private String djsj;
	
	
	public String getDjsj() {
		return djsj;
	}

	public void setDjsj(String djsj) {
		this.djsj = djsj;
	}

	public String getSjbh() {
		return sjbh;
	}

	public void setSjbh(String sjbh) {
		this.sjbh = sjbh;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getBookTime() {
		return bookTime;
	}

	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}

	public String getCertNumber() {
		return certNumber;
	}

	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
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

	public String getLocated() {
		return located;
	}

	public void setLocated(String located) {
		this.located = located;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(String seqNumber) {
		this.seqNumber = seqNumber;
	}

	public List<BaseZB05File> getFileList() {
		return fileList;
	}

	public void setFileList(List<BaseZB05File> fileList) {
		this.fileList = fileList;
	}

}
