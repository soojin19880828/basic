package com.thfdcsoft.framework.manage.entity;

public class Help {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column help.RECORD_ID
	 * @mbg.generated  Fri Jul 05 16:27:52 CST 2019
	 */
	private String recordId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column help.HELP_ID
	 * @mbg.generated  Fri Jul 05 16:27:52 CST 2019
	 */
	private String helpId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column help.HELP_TITLE
	 * @mbg.generated  Fri Jul 05 16:27:52 CST 2019
	 */
	private String helpTitle;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column help.HELP_CONTENT
	 * @mbg.generated  Fri Jul 05 16:27:52 CST 2019
	 */
	private String helpContent;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column help.RECORD_ID
	 * @return  the value of help.RECORD_ID
	 * @mbg.generated  Fri Jul 05 16:27:52 CST 2019
	 */
	public String getRecordId() {
		return recordId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column help.RECORD_ID
	 * @param recordId  the value for help.RECORD_ID
	 * @mbg.generated  Fri Jul 05 16:27:52 CST 2019
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column help.HELP_ID
	 * @return  the value of help.HELP_ID
	 * @mbg.generated  Fri Jul 05 16:27:52 CST 2019
	 */
	public String getHelpId() {
		return helpId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column help.HELP_ID
	 * @param helpId  the value for help.HELP_ID
	 * @mbg.generated  Fri Jul 05 16:27:52 CST 2019
	 */
	public void setHelpId(String helpId) {
		this.helpId = helpId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column help.HELP_TITLE
	 * @return  the value of help.HELP_TITLE
	 * @mbg.generated  Fri Jul 05 16:27:52 CST 2019
	 */
	public String getHelpTitle() {
		return helpTitle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column help.HELP_TITLE
	 * @param helpTitle  the value for help.HELP_TITLE
	 * @mbg.generated  Fri Jul 05 16:27:52 CST 2019
	 */
	public void setHelpTitle(String helpTitle) {
		this.helpTitle = helpTitle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column help.HELP_CONTENT
	 * @return  the value of help.HELP_CONTENT
	 * @mbg.generated  Fri Jul 05 16:27:52 CST 2019
	 */
	public String getHelpContent() {
		return helpContent;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column help.HELP_CONTENT
	 * @param helpContent  the value for help.HELP_CONTENT
	 * @mbg.generated  Fri Jul 05 16:27:52 CST 2019
	 */
	public void setHelpContent(String helpContent) {
		this.helpContent = helpContent;
	}

	@Override
	public String toString() {
		return "Help [recordId=" + recordId + ", helpId=" + helpId + ", helpTitle=" + helpTitle + ", helpContent="
				+ helpContent + "]";
	}

	
    
    
}