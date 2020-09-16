package com.whminwei.dto;

public class RealEstateInfo {

	private String bookTime;	//登簿时间
	private String certNumber; 				//权证号
	private String obligee;				      //权利人
	private String co_ownershipCircumstance;      		//共有情况
	private String located;						//坐落
	private String unitNumber;						//不动产单元号
	private String rightTypes;					//权利类型
	private String rightNature;					//权利性质
	private String application;					//用途
	private String area;						//面积
	private String serviceLife;						//使用期限
	private String other;						//权利其他情况
	private String excursus;						//附记
	private String fenbutu;		//分布图 base64

	private String zongditu;	//宗地图	base64
	private boolean paymentStatus;		//缴费状态
	private double cost;		//费用
	
	
	

	
	public RealEstateInfo(String bookTime, String certNumber, String obligee, String co_ownershipCircumstance,
			String located, String unitNumber, String rightTypes, String rightNature, String application, String area,
			String serviceLife, String other, String excursus, String fenbutu, String zongditu, boolean paymentStatus,
			double cost) {
		super();
		this.bookTime = bookTime;
		this.certNumber = certNumber;
		this.obligee = obligee;
		this.co_ownershipCircumstance = co_ownershipCircumstance;
		this.located = located;
		this.unitNumber = unitNumber;
		this.rightTypes = rightTypes;
		this.rightNature = rightNature;
		this.application = application;
		this.area = area;
		this.serviceLife = serviceLife;
		this.other = other;
		this.excursus = excursus;
		this.fenbutu = fenbutu;
		this.zongditu = zongditu;
		this.paymentStatus = paymentStatus;
		this.cost = cost;
	}


	public double getCost() {
		return cost;
	}


	@Override
	public String toString() {
		return "RealEstateInfo [bookTime=" + bookTime + ", certNumber=" + certNumber + ", obligee=" + obligee
				+ ", co_ownershipCircumstance=" + co_ownershipCircumstance + ", located=" + located + ", unitNumber="
				+ unitNumber + ", rightTypes=" + rightTypes + ", rightNature=" + rightNature + ", application="
				+ application + ", area=" + area + ", serviceLife=" + serviceLife + ", other=" + other + ", excursus="
				+ excursus + ", fenbutu=" + fenbutu + ", zongditu=" + zongditu + ", paymentStatus=" + paymentStatus
				+ ", cost=" + cost + "]";
	}


	public void setCost(double cost) {
		this.cost = cost;
	}


	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	

	public RealEstateInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getBookTime() {
		return bookTime;
	}
	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}
	public String getFenbutu() {
		return fenbutu;
	}
	public void setFenbutu(String fenbutu) {
		this.fenbutu = fenbutu;
	}
	public String getZongditu() {
		return zongditu;
	}
	public void setZongditu(String zongditu) {
		this.zongditu = zongditu;
	}
	public String getCertNumber() {
		return certNumber;
	}
	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}
	public String getObligee() {
		return obligee;
	}
	public void setObligee(String obligee) {
		this.obligee = obligee;
	}
	public String getCo_ownershipCircumstance() {
		return co_ownershipCircumstance;
	}
	public void setCo_ownershipCircumstance(String co_ownershipCircumstance) {
		this.co_ownershipCircumstance = co_ownershipCircumstance;
	}
	public String getLocated() {
		return located;
	}
	public void setLocated(String located) {
		this.located = located;
	}
	public String getUnitNumber() {
		return unitNumber;
	}
	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}
	public String getRightTypes() {
		return rightTypes;
	}
	public void setRightTypes(String rightTypes) {
		this.rightTypes = rightTypes;
	}
	public String getRightNature() {
		return rightNature;
	}
	public void setRightNature(String rightNature) {
		this.rightNature = rightNature;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getServiceLife() {
		return serviceLife;
	}
	public void setServiceLife(String serviceLife) {
		this.serviceLife = serviceLife;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getExcursus() {
		return excursus;
	}
	public void setExcursus(String excursus) {
		this.excursus = excursus;
	}
	
	
	
	
	
}
