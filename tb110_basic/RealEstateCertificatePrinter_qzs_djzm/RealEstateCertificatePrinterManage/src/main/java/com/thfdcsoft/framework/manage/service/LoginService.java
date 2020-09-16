package com.thfdcsoft.framework.manage.service;

import com.thfdcsoft.framework.manage.entity.UserInfo;

public interface LoginService {

	/**
	 * 根据登录账号查询用户信息
	 * 
	 * @param loginUsername
	 * @return
	 */
	public UserInfo selectByLoginUsername(String loginUsername);
}
