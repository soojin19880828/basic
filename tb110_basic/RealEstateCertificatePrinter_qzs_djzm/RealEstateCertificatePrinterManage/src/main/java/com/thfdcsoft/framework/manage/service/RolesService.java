package com.thfdcsoft.framework.manage.service;

import java.util.List;

public interface RolesService {

	/**
	 * 更新用户权限
	 * 
	 * @param roleId
	 * @param moduleIdList
	 * @return
	 */
	public boolean updateAuthorize(String roleId, List<String> moduleIdList);
	
	/**
	 * 删除角色
	 * 
	 * @param roleId
	 * @return
	 */
	public boolean cancleRole(String roleId);
}
