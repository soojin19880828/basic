package com.thfdcsoft.framework.manage.dto;

/**
 * 登记证明打印信息
 * 
 * @author 张嘉琪
 *
 */
public class SubmitPrintReq {

	// 业务受理号
	private String busiNumber;

	// 不动产权证书号
	private String certNumber;
	
	// 识别工本号
	private String sequNumber;

	// 登记证明扫描件
	private String certScan;
	
	// 使用记录ID
	private String UsageId;

	// 打印模块ID
	private String terminalId;

	public String getBusiNumber() {
		return busiNumber;
	}

	public void setBusiNumber(String busiNumber) {
		this.busiNumber = busiNumber;
	}

	public String getCertNumber() {
		return certNumber;
	}

	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}

	public String getSequNumber() {
		return sequNumber;
	}

	public void setSequNumber(String sequNumber) {
		this.sequNumber = sequNumber;
	}

	public String getCertScan() {
		return certScan;
	}

	public void setCertScan(String certScan) {
		this.certScan = certScan;
	}

	public String getUsageId() {
		return UsageId;
	}

	public void setUsageId(String usageId) {
		UsageId = usageId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	@Override
	public String toString() {
		return "SubmitPrintReq{" +
				"busiNumber='" + busiNumber + '\'' +
				", certNumber='" + certNumber + '\'' +
				", sequNumber='" + sequNumber + '\'' +
				", certScan='" + certScan + '\'' +
				", UsageId='" + UsageId + '\'' +
				", terminalId='" + terminalId + '\'' +
				'}';
	}
}
