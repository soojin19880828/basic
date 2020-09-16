package com.thfdcsoft.framework.manage.dao;

import java.util.List;

import com.thfdcsoft.framework.manage.entity.UserAuthorize;

/**
 * 用户授权信息数据持久层查询接口
 * 
 * @author 张嘉琪
 * @date 2017年11月21日下午2:46:22
 */
public interface UserAuthorizeMapper {

	public List<UserAuthorize> selectByUserId(String userId);
}
