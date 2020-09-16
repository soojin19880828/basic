package com.thfdcsoft.estate.printer.dto;

import com.deepoove.poi.config.Name;
import com.deepoove.poi.data.PictureRenderData;

public class DocxFile {

	/*
	 * 宗地图
	 */
	@Name("zongditu")
	private PictureRenderData zongditu;
	
	/*
	 * 分布图
	 */
	@Name("fenbutu")
	private PictureRenderData fenbutu;
	/*
	 * 权证号省 简称
	 */
	@Name("pro")
	private String pro;
	/*
	 * 权证号 年
	 */
	@Name("year")
	private String year;
	/*
	 * 权证号 城市
	 */
	@Name("city")
	private String city;
	/*
	 * 权证号 编号
	 */
	@Name("n")
	private String num;
	/*
	 * 权利人
	 */
	@Name("obligee")
	private String obligee;
	/*
	 * 共有情况
	 */
	@Name("ownership")
	private String co_ownershipCircumstance;
	/*
	 * 坐落
	 */
	@Name("located")
	private String located;//坐落
	/*
	 * 不动产单元号
	 */
	@Name("unitNumber")
	private String unitNumber;
	/*
	 * 权力类型
	 */
	@Name("rightTypes")
	private String rightTypes;
	/*
	 * 权力性质
	 */
	@Name("rightNature")
	private String rightNature;
	/*
	 * 用途
	 */
	@Name("application")
	private String application;
	/*
	 * 面积
	 */
	@Name("area")
	private String area;
	/*
	 * 使用期限
	 */
	@Name("serviceLife")
	private String serviceLife;
	/*
	 * 权力其他状况
	 */
	@Name("other")
	private String other;
	/*
	 * 附记
	 */
	@Name("excursus")
	private String excursus;
	/*
	 * 登簿 年
	 */
	@Name("year1")
	private String bookYear;
	/*
	 * 登簿 月
	 */
	@Name("month1")
	private String bookMonth;
	/*
	 * 登簿 日
	 */
	@Name("day1")
	private String bookDay;
	public PictureRenderData getZongditu() {
		return zongditu;
	}
	public void setZongditu(PictureRenderData zongditu) {
		this.zongditu = zongditu;
	}
	public PictureRenderData getFenbutu() {
		return fenbutu;
	}
	public void setFenbutu(PictureRenderData fenbutu) {
		this.fenbutu = fenbutu;
	}
	public String getPro() {
		return pro;
	}
	public void setPro(String pro) {
		this.pro = pro;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
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
	public String getBookYear() {
		return bookYear;
	}
	public void setBookYear(String bookYear) {
		this.bookYear = bookYear;
	}
	public String getBookMonth() {
		return bookMonth;
	}
	public void setBookMonth(String bookMonth) {
		this.bookMonth = bookMonth;
	}
	public String getBookDay() {
		return bookDay;
	}
	public void setBookDay(String bookDay) {
		this.bookDay = bookDay;
	}
	@Override
	public String toString() {
		return "DocxFile [zongditu=" + zongditu + ", fenbutu=" + fenbutu + ", pro=" + pro + ", year=" + year + ", city="
				+ city + ", num=" + num + ", obligee=" + obligee + ", co_ownershipCircumstance="
				+ co_ownershipCircumstance + ", located=" + located + ", unitNumber=" + unitNumber + ", rightTypes="
				+ rightTypes + ", rightNature=" + rightNature + ", application=" + application + ", area=" + area
				+ ", serviceLife=" + serviceLife + ", other=" + other + ", excursus=" + excursus + ", bookYear="
				+ bookYear + ", bookMonth=" + bookMonth + ", bookDay=" + bookDay + "]";
	}
	public DocxFile(PictureRenderData zongditu, PictureRenderData fenbutu, String pro, String year, String city,
			String num, String obligee, String co_ownershipCircumstance, String located, String unitNumber,
			String rightTypes, String rightNature, String application, String area, String serviceLife, String other,
			String excursus, String bookYear, String bookMonth, String bookDay) {
		super();
		this.zongditu = zongditu;
		this.fenbutu = fenbutu;
		this.pro = pro;
		this.year = year;
		this.city = city;
		this.num = num;
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
		this.bookYear = bookYear;
		this.bookMonth = bookMonth;
		this.bookDay = bookDay;
	}
	public DocxFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
}
