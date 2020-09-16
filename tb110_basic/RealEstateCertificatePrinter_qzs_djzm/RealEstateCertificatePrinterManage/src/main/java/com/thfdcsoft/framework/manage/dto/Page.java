package com.thfdcsoft.framework.manage.dto;

public class Page {

	private String draw;

	private String start;

	private String length;

	private String startTime;

	private String endTime;

	private String bizNumber;

	private String certNumber;

	private String printType;
	
	private String title;

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBizNumber() {
		return bizNumber;
	}

	public void setBizNumber(String bizNumber) {
		this.bizNumber = bizNumber;
	}

	public String getCertNumber() {
		return certNumber;
	}

	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	@Override
	public String toString() {
		return "Page{" +
				"draw='" + draw + '\'' +
				", start='" + start + '\'' +
				", length='" + length + '\'' +
				", startTime='" + startTime + '\'' +
				", endTime='" + endTime + '\'' +
				", bizNumber='" + bizNumber + '\'' +
				", certNumber='" + certNumber + '\'' +
				", printType='" + printType + '\'' +
				", title='" + title + '\'' +
				'}';
	}
}
