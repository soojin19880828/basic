package com.thfdcsoft.gov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 抵押信息对象类(YWLX=2或者YWLX=3或者YWLX=4才有值，否则为空)
 *
 */
public class DyxxInfo {
	/**债权数额*/
	@JsonProperty(value = "ZQSE")
	private Double zqse;

	/**起始日期*/
	@JsonProperty(value = "QSRQ")
	private String qsrq;

	/**终止日期*/
	@JsonProperty(value = "ZZRQ")
	private String zzrq;

	/**债务人*/
	@JsonProperty(value = "ZWR")
	private String zwr;

	/**债权人*/
	@JsonProperty(value = "ZQR")
	private String zqr;

	/**评估金额*/
	@JsonProperty(value = "PGJE")
	private Double pgje;

	/**抵押面积*/
	@JsonProperty(value = "DYMJ")
	private Double dymj;

	/**设定日期*/
	@JsonProperty(value = "SDRQ")
	private String sdrq;

	public Double getZqse() {
		return zqse;
	}

	public void setZqse(Double zqse) {
		this.zqse = zqse;
	}

	public String getQsrq() {
		return qsrq;
	}

	public void setQsrq(String qsrq) {
		this.qsrq = qsrq;
	}

	public String getZzrq() {
		return zzrq;
	}

	public void setZzrq(String zzrq) {
		this.zzrq = zzrq;
	}

	public String getZwr() {
		return zwr;
	}

	public void setZwr(String zwr) {
		this.zwr = zwr;
	}

	public String getZqr() {
		return zqr;
	}

	public void setZqr(String zqr) {
		this.zqr = zqr;
	}

	public Double getPgje() {
		return pgje;
	}

	public void setPgje(Double pgje) {
		this.pgje = pgje;
	}

	public Double getDymj() {
		return dymj;
	}

	public void setDymj(Double dymj) {
		this.dymj = dymj;
	}

	public String getSdrq() {
		return sdrq;
	}

	public void setSdrq(String sdrq) {
		this.sdrq = sdrq;
	}


}
