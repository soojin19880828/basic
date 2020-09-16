package com.thfdcsoft.framework.manage.entity;

/**
 * 页面显示用户管理信息对象类
 * 
 * @author 张嘉琪
 * @date 2017年11月27日上午10:42:29
 */
public class UserRole extends UserInfo {

	// 角色名称
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
