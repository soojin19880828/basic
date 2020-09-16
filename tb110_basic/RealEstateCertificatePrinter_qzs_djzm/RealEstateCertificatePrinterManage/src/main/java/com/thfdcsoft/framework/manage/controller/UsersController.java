package com.thfdcsoft.framework.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
import com.thfdcsoft.framework.common.string.StringUtils;
import com.thfdcsoft.framework.manage.constant.BaseConstant;
import com.thfdcsoft.framework.manage.dao.Loginreq;
import com.thfdcsoft.framework.manage.dao.RoleInfoMapper;
import com.thfdcsoft.framework.manage.dao.TerminalInfoMapper;
import com.thfdcsoft.framework.manage.dao.UserInfoMapper;
import com.thfdcsoft.framework.manage.dao.UserRoleMapper;
import com.thfdcsoft.framework.manage.entity.RoleInfo;
import com.thfdcsoft.framework.manage.entity.RoleInfoExample;
import com.thfdcsoft.framework.manage.entity.TerminalInfo;
import com.thfdcsoft.framework.manage.entity.UserInfo;
import com.thfdcsoft.framework.manage.entity.UserInfoExample;
import com.thfdcsoft.framework.manage.entity.UserInfoExample.Criteria;
import com.thfdcsoft.framework.manage.entity.UserRole;
import com.thfdcsoft.framework.manage.util.AutoIdFactory;
import com.thfdcsoft.framework.manage.util.Sha256;

/**
 * 用户管理页面控制器
 * 
 * @author 张嘉琪
 * @date 2017年11月27日上午9:23:17
 */
@Controller
@RequestMapping("/users")
public class UsersController extends BaseController {

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private RoleInfoMapper roleInfoMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private  TerminalInfoMapper terminalInfoMapper;

	/**
	 * 跳转至用户管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "modules/system/user/userUI";
	}

	/**
	 * 查询用户信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public void query(HttpServletRequest request, HttpServletResponse response) {
		List<UserRole> users = this.userRoleMapper.selectAll();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", JacksonFactory.writeJson(users));
		writeJSONResult(result, response);
	}

	/**
	 * 获取角色列表<br>
	 * 超级管理员角色不可选
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
	public void getRoleList(HttpServletRequest request, HttpServletResponse response) {
		RoleInfoExample example = new RoleInfoExample();
		RoleInfoExample.Criteria criteria = example.createCriteria();
		criteria.andRoleIdNotEqualTo(BaseConstant.DefaultRoles.ADMIN);
		List<RoleInfo> roles = this.roleInfoMapper.selectByExample(example);
		writeJSONResult(new BaseHttpRspn(!roles.isEmpty(), roles), response);
	}

	/**
	 * 校验登录账号是否重复
	 * 
	 * @param request
	 * @param response
	 * @param username
	 */
	@RequestMapping(value = "/checkUsername", method = RequestMethod.POST)
	public void checkUsername(HttpServletRequest request, HttpServletResponse response, String loginUsername) {
		UserInfoExample example = new UserInfoExample();
		UserInfoExample.Criteria criteria = example.createCriteria();
		criteria.andLoginUsernameEqualTo(loginUsername);
		List<UserInfo> users = this.userInfoMapper.selectByExample(example);
		writeJSONResult(users.isEmpty(), response);
	}

	/**
	 * 保存用户信息<br>
	 * userId为空时新增<br>
	 * userId不为空时更新
	 * 
	 * @param request
	 * @param response
	 * @param user
	 */
	@RequestMapping(value = "/submitUser", method = RequestMethod.POST)
	public void submitUser(HttpServletRequest request, HttpServletResponse response, UserInfo user) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		if (StringUtils.isNullOrEmpty(user.getUserId())) {
			user.setUserId(AutoIdFactory.getAutoId());
			user.setUserStatus(BaseConstant.UserStatus.ENABLE);
			int r = this.userInfoMapper.insertSelective(user);
			if (r > 0) {
				result.setResult(true);
				result.setRespObj(user);
				result.setRespMsg("新建用户成功，可修改后重复提交");
			} else {
				result.setResult(false);
				result.setRespMsg("新建用户失败，可修改后再次提交");
			}
		} else {
			int r = this.userInfoMapper.updateByPrimaryKeySelective(user);
			if (r > 0) {
				result.setResult(true);
				result.setRespObj(user);
				result.setRespMsg("编辑用户成功，可修改后重复提交");
			} else {
				result.setResult(false);
				result.setRespMsg("编辑用户失败，可修改后再次提交");
			}
		}
		writeJSONResult(result, response);
	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 */
	@RequestMapping(value = "/cancleUser", method = RequestMethod.POST)
	public void cancleUser(HttpServletRequest request, HttpServletResponse response, String userId) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		int r = this.userInfoMapper.deleteByPrimaryKey(userId);
		if (r > 0) {
			result.setResult(true);
			result.setRespMsg("删除用户成功");
		}else {
			result.setRespMsg("删除用户失败");
		}
		writeJSONResult(result, response);
	}

	/**
	 * 锁定用户
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 */
	@RequestMapping(value = "/lockUser", method = RequestMethod.POST)
	public void lockUser(HttpServletRequest request, HttpServletResponse response, String userId) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		UserInfo user = new UserInfo();
		user.setUserId(userId);
		user.setUserStatus(BaseConstant.UserStatus.LOCKED);
		int r = this.userInfoMapper.updateByPrimaryKeySelective(user);
		if (r > 0) {
			result.setResult(true);
			result.setRespMsg("锁定用户成功");
		}else {
			result.setRespMsg("锁定用户失败");
		}
		writeJSONResult(result, response);
	}

	/**
	 * 解锁用户
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 */
	@RequestMapping(value = "/unlockUser", method = RequestMethod.POST)
	public void unlockUser(HttpServletRequest request, HttpServletResponse response, String userId) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		UserInfo user = new UserInfo();
		user.setUserId(userId);
		user.setUserStatus(BaseConstant.UserStatus.ENABLE);
		int r = this.userInfoMapper.updateByPrimaryKeySelective(user);
		if (r > 0) {
			result.setResult(true);
			result.setRespMsg("解锁用户成功");
		}else {
			result.setRespMsg("解锁用户失败");
		}
		writeJSONResult(result, response);
	}
	
	
	
	//管理员验证
	@RequestMapping(value = "/manageYz", method = RequestMethod.POST)
	@ResponseBody
	public String manage(HttpServletRequest request, HttpServletResponse response,@RequestBody String idName) {	
		System.out.println("传到Manage的身份证号:"+idName);
		List<UserInfo> userInfo=userInfoMapper.selectIdName(idName);
		System.out.println(userInfo.toString());
		
		BaseHttpRspn result = new BaseHttpRspn(false);
		/*BaseHttpRspn rspn = JacksonFactory.readJson(userInfo, BaseHttpRspn.class);*/
		if(userInfo.size()!=0) {
			result.setResult(true);
		}else {
			result.setResult(false);
		}
		return JacksonFactory.writeJson(result);
	}
	
	
	//账号密码登陆
	@RequestMapping(value = "/zhLogin",method=RequestMethod.POST)
	@ResponseBody
	public String zhLogin(HttpServletRequest request, HttpServletResponse response,@RequestBody String writeJson) {	
		Loginreq req = JacksonFactory.readJson(writeJson, Loginreq.class);
	     String loginname = req.getLoginname();
	     String loginpassword = req.getLoginpassword();
	     
	     BaseHttpRspn result = new BaseHttpRspn(false);
	     
	     System.out.println("M____loginname:"+loginname);
	     System.out.println("M____loginpassword+"+loginpassword);
	     
	     //对密码进行加密
	     String sha256Str = Sha256.getSHA256Str(loginpassword).toUpperCase();
	     System.out.println("加密密码为:"+sha256Str);
	     
	     UserInfoExample userInfoExample = new UserInfoExample();
	     //Criteria实际上就是查询条件,在criteria中add的所有参数都会被select
	     //EqualTo是等于,也有小于、大于等方法，你可以自己看一下
	     Criteria criteria = userInfoExample.createCriteria();
	     criteria.andLoginUsernameEqualTo(loginname);
	     //这就是一些大于,大于等于,大于小于的方法,其它的就自己随便看看
//	     criteria.andLoginPasswordGreaterThan(value);
//	     criteria.andLoginPasswordGreaterThanOrEqualTo(value);
//	     criteria.andLoginPasswordBetween(value1, value2);
	     criteria.andLoginPasswordEqualTo(sha256Str);
	     
         List<UserInfo> selectByloginnamepwd = userInfoMapper.selectByExample(userInfoExample);
	     System.out.println("list"+selectByloginnamepwd.toString());
	     if(selectByloginnamepwd.size()!=0) {
	    	 result.setResult(true);
	    	 result.setRespMsg("登陆成功!");
	     }else if(selectByloginnamepwd.size()==0){
	    	 result.setResult(false);
	    	 result.setRespMsg("登陆失败!");
	     }
	     System.out.println(result.getRespMsg());
		return JacksonFactory.writeJson(result);
	}
	

	//判断Manage是否断开 decideManage
	@RequestMapping(value = "/decideManage", method = RequestMethod.GET)
	@ResponseBody
	public String decideManage(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Manage可以进入!!!!!!!!!!");
		BaseHttpRspn result = new BaseHttpRspn();
		result.setResult(true);
		result.setRespMsg("管理员可以进入");		
		System.out.println(JacksonFactory.writeJson(result));
		return JacksonFactory.writeJson(result);
	}	
		
}
