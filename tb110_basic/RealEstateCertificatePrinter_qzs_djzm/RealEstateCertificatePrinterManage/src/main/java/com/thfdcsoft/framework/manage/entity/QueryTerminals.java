package com.thfdcsoft.framework.manage.entity;

/**
 * 终端信息查询对象类
 * 
 * @author 张嘉琪
 * @date 2017年11月24日上午9:22:21
 */
public class QueryTerminals extends TerminalInfo {

	// 终端机名称|终端机型号信息表
	private String terminalName;
	
	// 部署地|行政区域信息表
	private String admiAreaName;

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public String getAdmiAreaName() {
		return admiAreaName;
	}

	public void setAdmiAreaName(String admiAreaName) {
		this.admiAreaName = admiAreaName;
	}
}
