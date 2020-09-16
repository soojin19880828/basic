package com.thfdcsoft.framework.manage.dao;

import java.util.List;

import com.thfdcsoft.framework.manage.entity.UserRole;

/**
 * 页面显示用户管理信息持久层查询接口
 * 
 * @author 张嘉琪
 * @date 2017年11月27日上午10:47:20
 */
public interface UserRoleMapper {

	public List<UserRole> selectAll();

}
