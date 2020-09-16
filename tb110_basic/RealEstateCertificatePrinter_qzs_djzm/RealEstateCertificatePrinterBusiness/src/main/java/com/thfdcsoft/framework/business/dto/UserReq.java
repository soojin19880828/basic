package com.thfdcsoft.framework.business.dto;

public class UserReq {
	
	private Integer uid;
	private String uname;
	private Integer age;
	private String sex;
	
	
	
	public UserReq(Integer uid, String uname, Integer age, String sex) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.age = age;
		this.sex = sex;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "UserReq [uid=" + uid + ", uname=" + uname + ", age=" + age + ", sex=" + sex + "]";
	}
	
	
}
