package com.thfdcsoft.framework.manage.dto;

public class UpdatePaperCountReq {
	private String usageId;
	private String terminalId;
	public String getUsageId() {
		return usageId;
	}
	public void setUsageId(String usageId) {
		this.usageId = usageId;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	@Override
	public String toString() {
		return "UpdatePaperCountReq [usageId=" + usageId + ", terminalId=" + terminalId + "]";
	}
	
	
	
}
