package com.thfdcsoft.framework.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thfdcsoft.framework.manage.dao.UserInfoMapper;
import com.thfdcsoft.framework.manage.entity.UserInfo;
import com.thfdcsoft.framework.manage.entity.UserInfoExample;
import com.thfdcsoft.framework.manage.service.LoginService;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public UserInfo selectByLoginUsername(String loginUsername) {
		UserInfoExample example = new UserInfoExample();
		UserInfoExample.Criteria criteria = example.createCriteria();
		criteria.andLoginUsernameEqualTo(loginUsername);
		List<UserInfo> userList = this.userInfoMapper.selectByExample(example);
		if (!userList.isEmpty()) {
			return userList.get(0);
		}
		return null;
	}
}
