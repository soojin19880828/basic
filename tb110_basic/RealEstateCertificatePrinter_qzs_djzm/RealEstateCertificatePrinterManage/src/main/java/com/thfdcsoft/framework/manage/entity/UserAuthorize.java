package com.thfdcsoft.framework.manage.entity;

/**
 * 用户授权信息对象类
 * 
 * @author 张嘉琪
 * @date 2017年11月21日下午2:41:16
 */
public class UserAuthorize extends ModuleInfo {

	// 用户ID|用户信息表
	private String userId;

	// 用户角色ID|用户信息表
	private String roleId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
