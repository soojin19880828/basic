package com.thfdcsoft.estate.printer.dto.req;

public class QueryPaperReq {
	// 设备编号
	private String deviceNumber;

	// 打印模块编号    
	private String terminalId;

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	@Override
	public String toString() {
		return "QueryPaperReq [deviceNumber=" + deviceNumber + ", terminalId=" + terminalId + "]";
	}
	
}
