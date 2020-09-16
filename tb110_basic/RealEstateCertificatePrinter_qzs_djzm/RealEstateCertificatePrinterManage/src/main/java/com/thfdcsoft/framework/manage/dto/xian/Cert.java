package com.thfdcsoft.framework.manage.dto.xian;


public class Cert {

	// 不动产权证号
	private String bdcqzh;

	// 印刷编号
	private String ysbh;

	public Cert() {

	}

	public Cert(String qzh,String ysbh) {
		this.setBdcqzh(qzh);
		this.setYsbh(ysbh);
	}

	public String getBdcqzh() {
		return bdcqzh;
	}

	public void setBdcqzh(String bdcqzh) {
		this.bdcqzh = bdcqzh;
	}

	public String getYsbh() {
		return ysbh;
	}

	public void setYsbh(String ysbh) {
		this.ysbh = ysbh;
	}

}
