package com.thfdcsoft.gov.dto.qzs;

import java.util.List;

public class HandleResult {

	/** 查询结果标识符 */
	private Boolean result;
	/** 异常类型 */
	private String exceptionType;
	/** 异常信息 */
	private String exceptionDetail;
	/** 查询到的数据 */
	private List<QzsEstateInfo> realEstateInfo;
	/** 正常数据 */
	private List<QzsEstateInfo> normalRealEstateInfo;
	/** 异常数据 */
	private List<QzsEstateInfo> abnormalRealEstateInfo;

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getExceptionDetail() {
		return exceptionDetail;
	}

	public void setExceptionDetail(String exceptionDetail) {
		this.exceptionDetail = exceptionDetail;
	}

	public List<QzsEstateInfo> getRealEstateInfo() {
		return realEstateInfo;
	}

	public void setRealEstateInfo(List<QzsEstateInfo> realEstateInfo) {
		this.realEstateInfo = realEstateInfo;
	}

	public List<QzsEstateInfo> getNormalRealEstateInfo() {
		return normalRealEstateInfo;
	}

	public void setNormalRealEstateInfo(List<QzsEstateInfo> normalRealEstateInfo) {
		this.normalRealEstateInfo = normalRealEstateInfo;
	}

	public List<QzsEstateInfo> getAbnormalRealEstateInfo() {
		return abnormalRealEstateInfo;
	}

	public void setAbnormalRealEstateInfo(List<QzsEstateInfo> abnormalRealEstateInfo) {
		this.abnormalRealEstateInfo = abnormalRealEstateInfo;
	}

	@Override
	public String toString() {
		return "HandleResult{" +
				"result=" + result +
				", exceptionType='" + exceptionType + '\'' +
				", exceptionDetail='" + exceptionDetail + '\'' +
				", realEstateInfo=" + realEstateInfo +
				", normalRealEstateInfo=" + normalRealEstateInfo +
				", abnormalRealEstateInfo=" + abnormalRealEstateInfo +
				'}';
	}

	public HandleResult(Boolean result) {
		super();
		this.result = result;
	}


}
