package com.thfdcsoft.framework.manage.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
import com.thfdcsoft.framework.common.string.StringUtils;
import com.thfdcsoft.framework.manage.constant.BaseConstant;
import com.thfdcsoft.framework.manage.dao.AuthorizeInfoMapper;
import com.thfdcsoft.framework.manage.dao.ModuleInfoMapper;
import com.thfdcsoft.framework.manage.dao.RoleInfoMapper;
import com.thfdcsoft.framework.manage.dao.UserInfoMapper;
import com.thfdcsoft.framework.manage.dto.ZNode;
import com.thfdcsoft.framework.manage.entity.AuthorizeInfoExample;
import com.thfdcsoft.framework.manage.entity.AuthorizeInfoKey;
import com.thfdcsoft.framework.manage.entity.ModuleInfo;
import com.thfdcsoft.framework.manage.entity.ModuleInfoExample;
import com.thfdcsoft.framework.manage.entity.RoleInfo;
import com.thfdcsoft.framework.manage.entity.RoleInfoExample;
import com.thfdcsoft.framework.manage.entity.UserInfo;
import com.thfdcsoft.framework.manage.entity.UserInfoExample;
import com.thfdcsoft.framework.manage.service.RolesService;
import com.thfdcsoft.framework.manage.util.AutoIdFactory;

/**
 * 角色管理页面控制器
 * 
 * @author 张嘉琪
 * @date 2017年11月27日上午11:15:11
 */
@Controller
@RequestMapping("/roles")
public class RolesController extends BaseController {

	@Autowired
	private RoleInfoMapper roleInfoMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private ModuleInfoMapper moduleInfoMapper;

	@Autowired
	private AuthorizeInfoMapper authorizeInfoMapper;

	@Autowired
	private RolesService rolesService;

	/**
	 * 跳转至角色管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "modules/system/role/roleUI";
	}

	/**
	 * 查询角色信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public void query(HttpServletRequest request, HttpServletResponse response) {
		List<RoleInfo> roles = this.roleInfoMapper.selectByExample(new RoleInfoExample());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", JacksonFactory.writeJson(roles));
		writeJSONResult(result, response);
	}

	/**
	 * 保存角色信息<br>
	 * roleId为空时新增<br>
	 * roleId不为空时更新
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping(value = "/submitRole", method = RequestMethod.POST)
	public void submitRole(HttpServletRequest request, HttpServletResponse response, RoleInfo role) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		if (StringUtils.isNullOrEmpty(role.getRoleId())) {
			role.setRoleId(AutoIdFactory.getAutoId());
			int r = this.roleInfoMapper.insertSelective(role);
			if (r > 0) {
				result.setResult(true);
				result.setRespObj(role);
				result.setRespMsg("新建角色成功，可修改后重复提交");
			} else {
				result.setResult(false);
				result.setRespMsg("新建角色失败，可修改后再次提交");
			}
		} else {
			int r = this.roleInfoMapper.updateByPrimaryKeySelective(role);
			if (r > 0) {
				result.setResult(true);
				result.setRespObj(role);
				result.setRespMsg("编辑角色成功，可修改后重复提交");
			} else {
				result.setResult(false);
				result.setRespMsg("编辑角色失败，可修改后再次提交");
			}
		}
		writeJSONResult(result, response);
	}

	/**
	 * 删除用户<br>
	 * 该角色下有用户存在时，不可删除
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 */
	@RequestMapping(value = "/cancleRole", method = RequestMethod.POST)
	public void cancleRole(HttpServletRequest request, HttpServletResponse response, String roleId) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		UserInfoExample example = new UserInfoExample();
		UserInfoExample.Criteria criteria = example.createCriteria();
		criteria.andRoleIdEqualTo(roleId);
		List<UserInfo> users = this.userInfoMapper.selectByExample(example);
		if (users.isEmpty()) {
			boolean r = this.rolesService.cancleRole(roleId);
			if (r) {
				result.setResult(true);
				result.setRespMsg("删除角色成功");
			} else {
				result.setRespMsg("删除角色失败");
			}
		} else {
			result.setRespMsg("该角色下有用户存在，不可执行删除操作");
		}
		writeJSONResult(result, response);
	}

	/**
	 * 获取功能列表<br>
	 * 并根据zTree规范重新封装节点
	 * 
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping(value = "/getModuleTree", method = RequestMethod.POST)
	public void getModuleTree(HttpServletRequest request, HttpServletResponse response, String roleId) {
		List<ModuleInfo> modules = this.moduleInfoMapper.selectByExample(new ModuleInfoExample());
		AuthorizeInfoExample example = new AuthorizeInfoExample();
		AuthorizeInfoExample.Criteria criteria = example.createCriteria();
		criteria.andRoleIdEqualTo(roleId);
		List<AuthorizeInfoKey> authorizes = this.authorizeInfoMapper.selectByExample(example);
		List<ZNode> zNodes = new ArrayList<ZNode>();
		for (ModuleInfo module : modules) {
			ZNode node = new ZNode();
			node.setId(module.getModuleId());
			if (BaseConstant.ModuleLevel.MODULE.equals(module.getModuleLevel())) {
				node.setpId(BaseConstant.DefaultZNode.ROOT_PID);
				node.setOpen(true);
			} else {
				node.setpId(module.getSupeModuleId());
			}
			String name = module.getModuleName();
			name += "[" + module.getModuleDescribe() + "]";
			node.setName(name);
			for (AuthorizeInfoKey authorize : authorizes) {
				if (authorize.getModuleId().equals(module.getModuleId())) {
					node.setChecked(true);
				}
			}
			zNodes.add(node);
		}
		Collections.sort(zNodes);
		writeJSONResult(new BaseHttpRspn(true, JacksonFactory.writeJson(zNodes)), response);
	}

	/**
	 * 为角色授权
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 */
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public void authorize(HttpServletRequest request, HttpServletResponse response, String modules, String roleId) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		List<String> moduleIdList = JacksonFactory.readJsonList(modules, String.class);
		boolean r = this.rolesService.updateAuthorize(roleId, moduleIdList);
		if (r) {
			result.setResult(true);
			result.setRespMsg("用户授权成功");
		} else {
			result.setResult(false);
			result.setRespMsg("用户授权失败");
		}
		writeJSONResult(result, response);

	}
}
