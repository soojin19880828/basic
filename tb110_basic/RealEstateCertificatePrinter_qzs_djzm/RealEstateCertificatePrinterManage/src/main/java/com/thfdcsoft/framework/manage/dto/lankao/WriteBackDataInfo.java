package com.thfdcsoft.framework.manage.dto.lankao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WriteBackDataInfo {
/*
 * 不动产证书打印回写信息DATA信息类
 */
	//受理编号
	@JsonProperty(value = "SLBH")
	private String slbh;
	
	//证书编号
	@JsonProperty(value = "ZSBH")
	private String zsbh;
	
	//证书印刷编号
	@JsonProperty(value = "YSBH")
	private String ysbh;
	
	//发证时间
	@JsonProperty(value = "FZSJ")
	private String fzsj;
	
	//发证人
	@JsonProperty(value = "FZR")
	private String fzr;
	
	//取件人
	@JsonProperty(value = "QJR")
	private String qjr;
	
	//取件人照片
	@JsonProperty(value = "QJRZP")
	private String qjrzp;
	
	//取件时间
	@JsonProperty(value = "QJSJ")
	private String qjsj;
	
	//证书照片
	@JsonProperty(value = "ZSZP")
	private List<String> zszp;

	public String getSlbh() {
		return slbh;
	}

	public void setSlbh(String slbh) {
		this.slbh = slbh;
	}

	public String getZsbh() {
		return zsbh;
	}

	public void setZsbh(String zsbh) {
		this.zsbh = zsbh;
	}

	public String getYsbh() {
		return ysbh;
	}

	public void setYsbh(String ysbh) {
		this.ysbh = ysbh;
	}

	public String getFzsj() {
		return fzsj;
	}

	public void setFzsj(String fzsj) {
		this.fzsj = fzsj;
	}

	public String getFzr() {
		return fzr;
	}

	public void setFzr(String fzr) {
		this.fzr = fzr;
	}

	public String getQjr() {
		return qjr;
	}

	public void setQjr(String qjr) {
		this.qjr = qjr;
	}

	public String getQjrzp() {
		return qjrzp;
	}

	public void setQjrzp(String qjrzp) {
		this.qjrzp = qjrzp;
	}

	public String getQjsj() {
		return qjsj;
	}

	public void setQjsj(String qjsj) {
		this.qjsj = qjsj;
	}

	public List<String> getZszp() {
		return zszp;
	}

	public void setZszp(List<String> zszp) {
		this.zszp = zszp;
	}
}
