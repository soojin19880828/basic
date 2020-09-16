package com.thfdcsoft.framework.business.dto;

import com.thfdcsoft.framework.business.entity.PrintRecord;

import java.util.List;

public class PrintRecordRsp {

	// 业务处理结果
	private Boolean result;

    private List<PrintRecord> printList;

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public List<PrintRecord> getPrintList() {
		return printList;
	}

	public void setPrintList(List<PrintRecord> printList) {
		this.printList = printList;
	}
}
