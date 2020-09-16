package com.thfdcsoft.framework.business.entity;

public class BankUserAll {

	private String bankId;

	private String bankName;

	private String bankUserName;

	private String bankUserIdCard;
	
	private String bankUserId;
	

	public String getBankUserId() {
		return bankUserId;
	}

	public void setBankUserId(String bankUserId) {
		this.bankUserId = bankUserId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public String getBankUserName() {
		return bankUserName;
	}

	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankUserIdCard() {
		return bankUserIdCard;
	}

	public void setBankUserIdCard(String bankUserIdCard) {
		this.bankUserIdCard = bankUserIdCard;
	}

}
