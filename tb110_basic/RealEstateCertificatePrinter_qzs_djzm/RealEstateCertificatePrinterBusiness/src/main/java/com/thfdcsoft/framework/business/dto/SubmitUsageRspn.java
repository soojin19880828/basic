package com.thfdcsoft.framework.business.dto;

/**
 * 提交终端使用记录后，返回的记录ID和剩余纸张数量
 * @author 张嘉琪
 *
 */
public class SubmitUsageRspn {

	private String usageId;
	
	private String remaining;

	public String getUsageId() {
		return usageId;
	}

	public void setUsageId(String usageId) {
		this.usageId = usageId;
	}

	public String getRemaining() {
		return remaining;
	}

	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}
}
