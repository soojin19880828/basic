package com.thfdcsoft.framework.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thfdcsoft.framework.manage.dao.AuthorizeInfoMapper;
import com.thfdcsoft.framework.manage.dao.RoleInfoMapper;
import com.thfdcsoft.framework.manage.entity.AuthorizeInfoExample;
import com.thfdcsoft.framework.manage.entity.AuthorizeInfoKey;
import com.thfdcsoft.framework.manage.service.RolesService;

@Service
@Transactional
public class RolesServiceImpl implements RolesService {

	@Autowired
	private AuthorizeInfoMapper authorizeInfoMapper;

	@Autowired
	private RoleInfoMapper roleInfoMapper;

	@Override
	public boolean updateAuthorize(String roleId, List<String> moduleIdList) {
		AuthorizeInfoExample example = new AuthorizeInfoExample();
		AuthorizeInfoExample.Criteria criteria = example.createCriteria();
		criteria.andRoleIdEqualTo(roleId);
		List<AuthorizeInfoKey> authorizes = this.authorizeInfoMapper.selectByExample(example);
		List<AuthorizeInfoKey> deleteAuthorizes = new ArrayList<AuthorizeInfoKey>();
		for (AuthorizeInfoKey authorize : authorizes) {
			if (moduleIdList.contains(authorize.getModuleId())) {
				moduleIdList.remove(authorize.getModuleId());
			} else {
				deleteAuthorizes.add(authorize);
			}
		}
		boolean result = true;
		for (String moduleId : moduleIdList) {
			AuthorizeInfoKey authorize = new AuthorizeInfoKey();
			authorize.setModuleId(moduleId);
			authorize.setRoleId(roleId);
			int i = this.authorizeInfoMapper.insertSelective(authorize);
			if (i <= 0) {
				result = false;
			}
		}
		for (AuthorizeInfoKey authorize : deleteAuthorizes) {
			int i = this.authorizeInfoMapper.deleteByPrimaryKey(authorize);
			if (i <= 0) {
				result = false;
			}
		}
		return result;
	}

	@Override
	public boolean cancleRole(String roleId) {
		int r = this.roleInfoMapper.deleteByPrimaryKey(roleId);
		if (r > 0) {
			AuthorizeInfoExample example = new AuthorizeInfoExample();
			AuthorizeInfoExample.Criteria criteria = example.createCriteria();
			criteria.andRoleIdEqualTo(roleId);
			r = this.authorizeInfoMapper.deleteByExample(example);
			if (r > 0) {
				return true;
			}
		}
		return false;
	}
}
